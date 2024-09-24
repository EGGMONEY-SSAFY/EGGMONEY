import { fileURLToPath, URL } from 'node:url';
import { defineConfig, loadEnv } from 'vite';
import vue from '@vitejs/plugin-vue';
import vueDevTools from 'vite-plugin-vue-devtools';
import { VitePWA } from 'vite-plugin-pwa';

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd());

  return {
    plugins: [
      vue(),
      vueDevTools(),
      VitePWA({
        registerType: 'autoUpdate',
        workbox: {
          runtimeCaching: [
            {
              urlPattern: /^https?:\/\/.*\/api\/kakao\/login/,
              handler: 'NetworkFirst',
            },
          ],
        },
        devOptions: {
          enabled: true,
        },
        srcDir: 'public',
        filename: 'sw.js',
        manifest: {
          name: 'My Vue App',
          short_name: 'VueApp',
          description: 'My awesome Vue application!',
          theme_color: '#FF5A00',
          background_color: '#F3F4F6',
          icons: [
            {
              src: 'img/icons/android-chrome-192x192.png',
              sizes: '192x192',
              type: 'image/png',
            },
            {
              src: '/img/icons/android-chrome-512x512.png',
              sizes: '512x512',
              type: 'image/png',
            },
          ],
        },
      }),
    ],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url)),
      },
    },
    server: {
      proxy: {
        [env.VITE_BASE_URL]: {
          target: env.VITE_SERVER_URL, // 환경 변수에서 서버 URL 가져오기
          changeOrigin: true,
          secure: false,
          rewrite: (path) => path.replace(new RegExp(`^${env.VITE_BASE_URL}`), ''),
        },
      },
    },
  };
});
