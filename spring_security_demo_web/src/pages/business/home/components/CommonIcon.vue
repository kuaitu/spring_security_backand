<template>
  <component :is="iconType"
             :type="iconName"
             :color="iconColor"
             :size="iconSize" />
</template>

<script>
  import Icons from './Icons'

  export default {
  name: 'CommonIcon',

  props: {
    type: {
      type: String,
      required: true
    },
    color: String,
    size: Number
  },

  methods: {
    getCustomIconName (iconName) {
      return iconName.slice(1)
    }
  },

  computed: {
    iconType () {
      return this.type.indexOf('_') === 0 ? 'Icons' : 'Icon'
    },
    iconName () {
      return this.iconType === 'Icons' ? this.getCustomIconName(this.type) : this.type
    },
    iconSize () {
      return this.size || (this.iconType === 'Icons' ? 12 : undefined)
    },
    iconColor () {
      return this.color || ''
    }
  },

  components: { Icons }
}
</script>

<style>
</style>
