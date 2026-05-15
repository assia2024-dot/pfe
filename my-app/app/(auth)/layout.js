export default function AuthLayout({ children }) {
  return (
    <div 
      className="min-h-screen w-full flex items-center justify-center bg-cover bg-center bg-no-repeat"
      style={{
        backgroundImage: "url('/bg.jpg')",
      }}
    >
      {/* Lighter overlay */}
      <div className="min-h-screen w-full flex items-center justify-center bg-black/20 backdrop-blur-sm">
        {children}
      </div>
    </div>
  )
}