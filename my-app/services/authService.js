import api from "@/lib/axios"

export const authService = {
  login: (email, password) =>
    api.post("/api/auth/login", { email, password }),

  verify2fa: (email, code) =>
    api.post("/api/auth/verify-2fa", { email, code }),

  logout: () =>
    api.post("/api/auth/logout"),

  forgotPassword: (email) =>
    api.post("/api/auth/forgot-password", { email }),

  verifyResetCode: (email, code) =>
    api.post("/api/auth/verify-reset-code", { email, code }),

  resetPassword: (email, code, newPassword) =>
    api.post("/api/auth/reset-password", { email, code, newPassword }),
  
  resendLoginCode: (email) =>
  api.post("/api/auth/resend-login-code", { email }),

  resendResetCode: (email) =>
  api.post("/api/auth/resend-reset-code", { email }),
}