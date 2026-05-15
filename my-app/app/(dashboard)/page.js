export default function Home() {
  return (
    <div className="w-full h-full">
      <iframe
        title="dashboard"
        src="https://app.powerbi.com/reportEmbed?reportId=12bc76dc-4838-4794-a2b3-3132980bca06&autoAuth=true&ctid=04d6a2f0-64b2-4e71-b348-646401d08ee8"
        allowFullScreen
        className="w-full h-full border-0 rounded-md"
        style={{ minHeight: "calc(100vh - 120px)" }}
      />
    </div>
  )
}