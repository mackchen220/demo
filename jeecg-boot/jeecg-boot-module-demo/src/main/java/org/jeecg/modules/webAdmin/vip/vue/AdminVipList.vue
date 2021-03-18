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
      <a-button type="primary" icon="download" @click="handleExportXls('会员设置')">导出</a-button>
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

    <admin-vip-modal ref="modalForm" @ok="modalFormOk"></admin-vip-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AdminVipModal from './modules/AdminVipModal'

  export default {
    name: 'AdminVipList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      AdminVipModal
    },
    data () {
      return {
        description: '会员设置管理页面',
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
            title:'会员名称',
            align:"center",
            dataIndex: 'vipName'
          },
          {
            title:'限时价格',
            align:"center",
            dataIndex: 'priceLow'
          },
          {
            title:'原价格',
            align:"center",
            dataIndex: 'priceHigh'
          },
          {
            title:'佣金百分比，例：百分之五十，数据为50',
            align:"center",
            dataIndex: 'commission'
          },
          {
            title:'奖金区间小',
            align:"center",
            dataIndex: 'bonusHigh'
          },
          {
            title:'奖金区间大',
            align:"center",
            dataIndex: 'bonusLow'
          },
          {
            title:'整形折扣百分比 例：五折，数据为50',
            align:"center",
            dataIndex: 'discount'
          },
          {
            title:'旅游次数',
            align:"center",
            dataIndex: 'times'
          },
          {
            title:'区域分红百分比',
            align:"center",
            dataIndex: 'areaCommission'
          },
          {
            title:'限时名额数量',
            align:"center",
            dataIndex: 'quotaNum'
          },
          {
            title:'开始时间',
            align:"center",
            dataIndex: 'beginTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'结束日期',
            align:"center",
            dataIndex: 'endTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'删除标识0-正常,1-已删除',
            align:"center",
            dataIndex: 'delFlag'
          },
          {
            title:'vip卡图标',
            align:"center",
            dataIndex: 'image',
            scopedSlots: {customRender: 'imgSlot'}
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
          list: "/vip/adminVip/list",
          delete: "/vip/adminVip/delete",
          deleteBatch: "/vip/adminVip/deleteBatch",
          exportXlsUrl: "/vip/adminVip/exportXls",
          importExcelUrl: "vip/adminVip/importExcel",
          
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
        fieldList.push({type:'string',value:'vipName',text:'会员名称',dictCode:''})
        fieldList.push({type:'int',value:'priceLow',text:'限时价格',dictCode:''})
        fieldList.push({type:'int',value:'priceHigh',text:'原价格',dictCode:''})
        fieldList.push({type:'int',value:'commission',text:'佣金百分比，例：百分之五十，数据为50',dictCode:''})
        fieldList.push({type:'int',value:'bonusHigh',text:'奖金区间小',dictCode:''})
        fieldList.push({type:'int',value:'bonusLow',text:'奖金区间大',dictCode:''})
        fieldList.push({type:'int',value:'discount',text:'整形折扣百分比 例：五折，数据为50',dictCode:''})
        fieldList.push({type:'int',value:'times',text:'旅游次数',dictCode:''})
        fieldList.push({type:'int',value:'areaCommission',text:'区域分红百分比',dictCode:''})
        fieldList.push({type:'int',value:'quotaNum',text:'限时名额数量',dictCode:''})
        fieldList.push({type:'date',value:'beginTime',text:'开始时间'})
        fieldList.push({type:'date',value:'endTime',text:'结束日期'})
        fieldList.push({type:'int',value:'delFlag',text:'删除标识0-正常,1-已删除',dictCode:''})
        fieldList.push({type:'string',value:'image',text:'vip卡图标',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>