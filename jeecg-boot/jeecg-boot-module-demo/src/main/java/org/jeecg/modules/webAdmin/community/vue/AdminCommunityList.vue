<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="审核状态 0待审核 1 审核未通过2审核通过">
              <a-input placeholder="请输入审核状态 0待审核 1 审核未通过2审核通过" v-model="queryParam.checkStatus"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('朋友圈')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <admin-community-modal ref="modalForm" @ok="modalFormOk"></admin-community-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AdminCommunityModal from './modules/AdminCommunityModal'

  export default {
    name: 'AdminCommunityList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      AdminCommunityModal
    },
    data () {
      return {
        description: '朋友圈管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'userId',
            align:"center",
            dataIndex: 'userId'
          },
          {
            title:'内容',
            align:"center",
            dataIndex: 'content'
          },
          {
            title:'链接',
            align:"center",
            dataIndex: 'url'
          },
          {
            title:'图片或者视频地址',
            align:"center",
            dataIndex: 'imageUrl',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'定位城市',
            align:"center",
            dataIndex: 'city'
          },
          {
            title:'朋友圈类型:1 照片 2视频 3课程  4其他  ',
            align:"center",
            dataIndex: 'type'
          },
          {
            title:'1-用户，2-达人，3-机构，4-平台',
            align:"center",
            dataIndex: 'userType'
          },
          {
            title:'收藏数量',
            align:"center",
            dataIndex: 'starNum'
          },
          {
            title:'点赞数量',
            align:"center",
            dataIndex: 'goodNum'
          },
          {
            title:'转发数量',
            align:"center",
            dataIndex: 'forwardNum'
          },
          {
            title:'观看数量',
            align:"center",
            dataIndex: 'watchNum'
          },
          {
            title:'审核状态 0待审核 1 审核未通过2审核通过',
            align:"center",
            dataIndex: 'checkStatus'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/community/adminCommunity/list",
          delete: "/community/adminCommunity/delete",
          deleteBatch: "/community/adminCommunity/deleteBatch",
          exportXlsUrl: "/community/adminCommunity/exportXls",
          importExcelUrl: "community/adminCommunity/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'userId',text:'userId',dictCode:''})
        fieldList.push({type:'string',value:'content',text:'内容',dictCode:''})
        fieldList.push({type:'string',value:'url',text:'链接',dictCode:''})
        fieldList.push({type:'string',value:'imageUrl',text:'图片或者视频地址',dictCode:''})
        fieldList.push({type:'string',value:'city',text:'定位城市',dictCode:''})
        fieldList.push({type:'int',value:'type',text:'朋友圈类型:1 照片 2视频 3课程  4其他  ',dictCode:''})
        fieldList.push({type:'int',value:'userType',text:'1-用户，2-达人，3-机构，4-平台',dictCode:''})
        fieldList.push({type:'int',value:'starNum',text:'收藏数量',dictCode:''})
        fieldList.push({type:'int',value:'goodNum',text:'点赞数量',dictCode:''})
        fieldList.push({type:'int',value:'forwardNum',text:'转发数量',dictCode:''})
        fieldList.push({type:'int',value:'watchNum',text:'观看数量',dictCode:''})
        fieldList.push({type:'int',value:'checkStatus',text:'审核状态 0待审核 1 审核未通过2审核通过',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>