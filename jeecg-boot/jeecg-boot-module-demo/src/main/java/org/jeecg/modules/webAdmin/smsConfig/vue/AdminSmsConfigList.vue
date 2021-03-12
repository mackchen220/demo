<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('短信配置')">导出</a-button>
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

    <admin-sms-config-modal ref="modalForm" @ok="modalFormOk"></admin-sms-config-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AdminSmsConfigModal from './modules/AdminSmsConfigModal'

  export default {
    name: 'AdminSmsConfigList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      AdminSmsConfigModal
    },
    data () {
      return {
        description: '短信配置管理页面',
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
            title:'短信接口地址',
            align:"center",
            dataIndex: 'sendApi'
          },
          {
            title:'接口账号 (必填)（企业登录名）',
            align:"center",
            dataIndex: 'loginName'
          },
          {
            title:'短信内容模板',
            align:"center",
            dataIndex: 'sendContent'
          },
          {
            title:'密码 (必填)（企业账号对应密码）',
            align:"center",
            dataIndex: 'password'
          },
          {
            title:'签名（可为空）',
            align:"center",
            dataIndex: 'signName'
          },
          {
            title:'计费套餐类型 (必填)2 行业套餐 3 为政务套餐',
            align:"center",
            dataIndex: 'feeType'
          },
          {
            title:'删除标识0-正常,1-已删除',
            align:"center",
            dataIndex: 'delFlag'
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
          list: "/smsConfig/adminSmsConfig/list",
          delete: "/smsConfig/adminSmsConfig/delete",
          deleteBatch: "/smsConfig/adminSmsConfig/deleteBatch",
          exportXlsUrl: "/smsConfig/adminSmsConfig/exportXls",
          importExcelUrl: "smsConfig/adminSmsConfig/importExcel",
          
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
        fieldList.push({type:'string',value:'sendApi',text:'短信接口地址'})
        fieldList.push({type:'string',value:'loginName',text:'接口账号 (必填)（企业登录名）'})
        fieldList.push({type:'string',value:'sendContent',text:'短信内容模板'})
        fieldList.push({type:'string',value:'password',text:'密码 (必填)（企业账号对应密码）'})
        fieldList.push({type:'string',value:'signName',text:'签名（可为空）'})
        fieldList.push({type:'string',value:'feeType',text:'计费套餐类型 (必填)2 行业套餐 3 为政务套餐'})
        fieldList.push({type:'int',value:'delFlag',text:'删除标识0-正常,1-已删除'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>