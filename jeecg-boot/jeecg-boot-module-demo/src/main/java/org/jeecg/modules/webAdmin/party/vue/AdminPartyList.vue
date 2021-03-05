<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="派对标题">
              <a-input placeholder="请输入派对标题" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="简介">
              <a-input placeholder="请输入简介" v-model="queryParam.profiles"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="城市">
                <a-input placeholder="请输入城市" v-model="queryParam.city"></a-input>
              </a-form-item>
            </a-col>
          </template>
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
      <a-button type="primary" icon="download" @click="handleExportXls('社群活动')">导出</a-button>
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

    <admin-party-modal ref="modalForm" @ok="modalFormOk"></admin-party-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AdminPartyModal from './modules/AdminPartyModal'

  export default {
    name: 'AdminPartyList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      AdminPartyModal
    },
    data () {
      return {
        description: '社群活动管理页面',
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
            title:'派对标题',
            align:"center",
            dataIndex: 'title'
          },
          {
            title:'封面图',
            align:"center",
            dataIndex: 'image',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'简介',
            align:"center",
            dataIndex: 'profiles'
          },
          {
            title:'价格',
            align:"center",
            dataIndex: 'price'
          },
          {
            title:'名额',
            align:"center",
            dataIndex: 'num'
          },
          {
            title:'发起人id、',
            align:"center",
            dataIndex: 'userId'
          },
          {
            title:'微信群二维码',
            align:"center",
            dataIndex: 'qrCode',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'开始时间',
            align:"center",
            dataIndex: 'startTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'结束时间',
            align:"center",
            dataIndex: 'endTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'活动地址',
            align:"center",
            dataIndex: 'address'
          },
          {
            title:'活动详情图片',
            align:"center",
            dataIndex: 'detailImgae',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'删除标识0-正常,1-已删除',
            align:"center",
            dataIndex: 'delFlag'
          },
          {
            title:'观看数量',
            align:"center",
            sorter: true,
            dataIndex: 'watchNum'
          },
          {
            title:'点赞数量',
            align:"center",
            sorter: true,
            dataIndex: 'goodNum'
          },
          {
            title:'收藏数量',
            align:"center",
            sorter: true,
            dataIndex: 'starNum'
          },
          {
            title:'转发数量',
            align:"center",
            sorter: true,
            dataIndex: 'forwardNum'
          },
          {
            title:'城市',
            align:"center",
            dataIndex: 'city'
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
          list: "/party/adminParty/list",
          delete: "/party/adminParty/delete",
          deleteBatch: "/party/adminParty/deleteBatch",
          exportXlsUrl: "/party/adminParty/exportXls",
          importExcelUrl: "party/adminParty/importExcel",
          
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
        fieldList.push({type:'string',value:'title',text:'派对标题',dictCode:''})
        fieldList.push({type:'string',value:'image',text:'封面图',dictCode:''})
        fieldList.push({type:'string',value:'profiles',text:'简介',dictCode:''})
        fieldList.push({type:'string',value:'price',text:'价格',dictCode:''})
        fieldList.push({type:'int',value:'num',text:'名额',dictCode:''})
        fieldList.push({type:'string',value:'userId',text:'发起人id、',dictCode:''})
        fieldList.push({type:'string',value:'qrCode',text:'微信群二维码',dictCode:''})
        fieldList.push({type:'date',value:'startTime',text:'开始时间'})
        fieldList.push({type:'date',value:'endTime',text:'结束时间'})
        fieldList.push({type:'string',value:'address',text:'活动地址',dictCode:''})
        fieldList.push({type:'string',value:'detailImgae',text:'活动详情图片',dictCode:''})
        fieldList.push({type:'int',value:'delFlag',text:'删除标识0-正常,1-已删除',dictCode:''})
        fieldList.push({type:'string',value:'watchNum',text:'观看数量',dictCode:''})
        fieldList.push({type:'string',value:'goodNum',text:'点赞数量',dictCode:''})
        fieldList.push({type:'int',value:'starNum',text:'收藏数量',dictCode:''})
        fieldList.push({type:'int',value:'forwardNum',text:'转发数量',dictCode:''})
        fieldList.push({type:'string',value:'city',text:'城市',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>