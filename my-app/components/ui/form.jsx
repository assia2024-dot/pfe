"use client";
import * as React from "react"
import { Slot } from "@radix-ui/react-slot"

import { cn } from "@/lib/utils"
import { Label } from "@/components/ui/label"

const FormFieldContext = React.createContext(null)

function useFormField() {
  const ctx = React.useContext(FormFieldContext)
  if (!ctx) throw new Error("useFormField must be used within FormField")
  return ctx
}

// Normalize errors to strings (handles Zod error objects, strings, etc.)
function normalizeErrors(errors) {
  return errors
    .map((e) => {
      if (typeof e === "string") return e
      if (e && typeof e === "object" && "message" in e) return String(e.message);
      return String(e);
    })
    .filter(Boolean);
}

// FormField - Context provider
function FormField({
  children,
  name,
  errors = [],
  isTouched = false,
  isValidating = false,
  orientation = "vertical",
  className,
  ...props
}) {
  const id = React.useId()
  const normalizedErrors = normalizeErrors(errors)

  return (
    <FormFieldContext.Provider value={{ id, name, errors: normalizedErrors, isTouched, isValidating }}>
      <div
        data-slot="form-field"
        data-orientation={orientation}
        className={cn(
          orientation === "vertical" ? "space-y-2" : "flex items-start gap-3",
          className
        )}
        {...props}>
        {children}
      </div>
    </FormFieldContext.Provider>
  );
}

// FormLabel - Label with error state
function FormLabel({
  className,
  ...props
}) {
  const { id, errors, isTouched } = useFormField()
  const hasError = isTouched && errors.length > 0
  return (
    <Label
      data-slot="form-label"
      htmlFor={id}
      data-invalid={hasError || undefined}
      className={cn("data-[invalid]:text-destructive", className)}
      {...props} />
  );
}

// FormControl - Slot with auto aria-invalid
function FormControl({
  children
}) {
  const { id, errors, isTouched } = useFormField()
  const hasError = isTouched && errors.length > 0
  return (
    <Slot
      id={id}
      aria-invalid={hasError || undefined}
      aria-describedby={hasError ? `${id}-message` : `${id}-description`}>
      {children}
    </Slot>
  );
}

// FormDescription - Help text
function FormDescription({
  className,
  ...props
}) {
  const { id } = useFormField()
  return (
    <p
      data-slot="form-description"
      id={`${id}-description`}
      className={cn("text-muted-foreground text-sm", className)}
      {...props} />
  );
}

// FormMessage - Error messages
function FormMessage({
  className,
  ...props
}) {
  const { id, errors, isTouched } = useFormField()
  if (!isTouched || errors.length === 0) return null
  return (
    <p
      data-slot="form-message"
      id={`${id}-message`}
      role="alert"
      className={cn("text-destructive text-sm font-medium", className)}
      {...props}>
      {errors.join(", ")}
    </p>
  );
}

// Form - Form wrapper
function Form({
  className,
  ...props
}) {
  return (<form data-slot="form" className={cn("space-y-6", className)} {...props} />);
}

export {
  Form,
  FormField,
  FormLabel,
  FormControl,
  FormDescription,
  FormMessage,
  useFormField,
}
