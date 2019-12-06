<template>
  <div class="prompt-box">
    <div class="prompt-box__card">
      <p class="prompt-box__title">
        <Icon type="speakerphone" class="prompt-box__icon"></Icon>
        温馨提示
      </p>
      <ul class="prompt-box__list">
        <!-- <li v-if="promptList" v-for="prom in promptList"> -->
          {{promptList.text}}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PromptBox',
  props: ['promptList']
}
</script>

<style lang="less" scoped>
.prompt-box {
  border-left: 5px solid rgb(237,210,138);

  &__card {
    background:rgb(255,255,220);
    padding: 5px 0px;
  }

  &__title {
    font-weight: bold;
    line-height: 30px;
  }

  &__icon {
    margin: 0px 10px;
  }

  &__list {
    list-style-type: disc;
    line-height: 20px;
    padding-left: 25px;
  }

  &__list>li {
    padding-left: 8px;
  }
}
</style>
