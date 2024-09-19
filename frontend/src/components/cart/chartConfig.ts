export const data = {
  labels: ["코스피", "코스닥", "반도체", "바이오"],
  datasets: [
    {
      data: [30000, 20000, 10000, 5000],
    },
  ],
}

export const options = {
  responsive: true,
  maintainAspectRatio: true,
  hoverOffset: 50,
  borderDashOffset: 10,
  layout: {
    padding: {
      top: 100,
      bottom: 140,
    },
  },
}
