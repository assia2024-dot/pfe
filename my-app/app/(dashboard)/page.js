export default function Home() {
  const reportBase =
    "https://app.powerbi.com/reportEmbed?reportId=12bc76dc-4838-4794-a2b3-3132980bca06&autoAuth=true&ctid=04d6a2f0-64b2-4e71-b348-646401d08ee8&navContentPaneEnabled=false&filterPaneEnabled=false"

  const pages = [
    { label: "Page 1", hash: "7e05ba51cec3b18cf3d4", height: "calc(100vh - 120px)" },
    { label: "Page 2", hash: "05050f55c663ded112c9", height: "calc(100vh - 120px)" },
    { label: "Page 3", hash: "97c41693db472cbacb8b", height: "calc(100vh - 120px)" }, // taller
  ]

  return (
    <div className="w-full overflow-y-auto">
      {pages.map((page) => (
        <div key={page.hash + page.label}>
          <iframe
            title={page.label}
            src={`${reportBase}&pageName=${page.hash}`}
            allowFullScreen
            className="w-full border-0"
            style={{ height: page.height }}
          />
        </div>
      ))}
    </div>
  )
}