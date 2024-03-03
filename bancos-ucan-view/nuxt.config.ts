// https://nuxt.com/docs/api/configuration/nuxt-config  
export default defineNuxtConfig({
  modules: [
      'nuxt-primevue'
  ],
  primevue: {
    components: {
        include: '*'
        // exclude: ['Galleria', 'Carousel']
    },
    directives: {
      prefix: 'p',
      include: ['Ripple', 'Tooltip']    /* Used as v-pripple and v-ptooltip */
  },
  options: {
    ripple: true,
    inputStyle: 'filled'
  }
  },

  css: ['primevue/resources/themes/aura-dark-amber/theme.css'],
  alias:{
    "assets/**": "@assets"
  }
})
