/** @type {import('tailwindcss').Config} */
// import flowbitePlugin from 'flowbite/plugin'

export default {
  content: [
    "./index.html",
    "./src/**/*.{html,vue,js,ts,jsx,tsx}",
    "./node_modules/flowbite/**/*.{js,ts}"
  ],
  theme: {
    extend: {
      colors: {
        'main-color': '#FF5A00',
      }
    },
  },
  plugins: [
    // flowbitePlugin // Flowbite 플러그인 사용
  ],
}
