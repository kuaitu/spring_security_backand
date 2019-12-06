<template>
  <div>
    <Modal v-model="isOpenModal"
           width="900"
           :closable="true"
           :mask-closable="false"
           title="高级搜索">
      <slot name="advancedSearchForm"></slot>
      <div slot="footer"
           style="text-align:left">
        <Button @click="resetFields"
                type="info">重置</Button>
        <Button @click="closeModal">取消</Button>
        <Button @click="submit"
                type="primary">确定</Button>
      </div>
    </Modal>
    <div v-if="!isAdvancedSearch">
      <div class="table-cell">
        <slot name="simpleSearchForm"></slot>
      </div>
      <div class="table-cell">
        <Button @click="openModal"
                type="info"
                class="search-btn">高级搜索</Button>
      </div>
    </div>
    <div v-if="isAdvancedSearch">
      <div class="table-cell search-result">
        <label style="margin-left:8px;">搜索结果</label>
        <Divider type="vertical"
                 style="margin-left: 12px;margin-right:12px;background:#c8caca" />
      </div>
      <div class="table-cell">
        <Button @click="clearSearch"
                icon="ios-share-alt-outline"
                class="search-btn">清除搜索</Button>
        <Button @click="isOpenModal = true"
                type="primary"
                icon="ios-search"
                class="search-btn">继续搜索</Button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  prop: [],
  data () {
    return {
      isOpenModal: false,
      isAdvancedSearch: false
    }
  },
  methods: {
    resetFields () {
      // 清空高级搜索form
      this.$emit('doReset')
    },
    openModal () {
      this.isOpenModal = true
    },
    closeModal () {
      this.isOpenModal = false
    },
    submit () {
      this.isAdvancedSearch = true
      this.closeModal()
      this.$emit('doSearch')
    },
    clearSearch () {
      this.isAdvancedSearch = false
      this.$emit('doReset')
      this.$emit('doSearch')
    }
  }
}
</script>
<style lang="less" scoped>
.table-cell {
  display: table-cell;
  vertical-align: middle;
}
.search-result {
  padding-bottom: 11px;
}
.search-btn {
  margin-left: 8px;
  margin-bottom: 11px;
}
</style>
