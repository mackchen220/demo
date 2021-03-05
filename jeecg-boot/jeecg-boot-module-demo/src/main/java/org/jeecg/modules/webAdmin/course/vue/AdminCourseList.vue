<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="title">
              <a-input placeholder="请输入title" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item label="价格">
              <a-input placeholder="请输入最小值" class="query-group-cust" v-model="queryParam.price_begin"></a-input>
              <span class="query-group-split-cust"></span>
              <a-input placeholder="请输入最大值" class="query-group-cust" v-model="queryParam.price_end"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="类型1 -收费,2 免费， 3限时免费">
                <a-input placeholder="请输入类型1 -收费,2 免费， 3限时免费" v-model="queryParam.type"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="标识0-完结,1-正在更新">
                <a-input placeholder="请输入标识0-完结,1-正在更新" v-model="queryParam.state"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目">
                <a-input placeholder="请输入内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目" v-model="queryParam.contentType"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('课程管理')">导出</a-button>
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

    <admin-course-modal ref="modalForm" @ok="modalFormOk"></admin-course-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AdminCourseModal from './modules/AdminCourseModal'

  export default {
    name: 'AdminCourseList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      AdminCourseModal
    },
    data () {
      return {
        description: '课程管理管理页面',
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
            title:'title',
            align:"center",
            dataIndex: 'title'
          },
          {
            title:'课程地址',
            align:"center",
            dataIndex: 'url'
          },
          {
            title:'封面图片',
            align:"center",
            dataIndex: 'image',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'价格',
            align:"center",
            sorter: true,
            dataIndex: 'price'
          },
          {
            title:'类型1 -收费,2 免费， 3限时免费',
            align:"center",
            dataIndex: 'type_dictText'
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
            title:'标识0-完结,1-正在更新',
            align:"center",
            sorter: true,
            dataIndex: 'state'
          },
          {
            title:'排序字段',
            align:"center",
            sorter: true,
            dataIndex: 'sort'
          },
          {
            title:'删除标识0-正常,1-已删除',
            align:"center",
            dataIndex: 'delFlag'
          },
          {
            title:'课程类型 1-文章 2-视频',
            align:"center",
            dataIndex: 'courseType'
          },
          {
            title:'城市',
            align:"center",
            dataIndex: 'city'
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
            title:'内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目',
            align:"center",
            dataIndex: 'contentType'
          },
          {
            title:'设为推荐 0 不推荐 1推荐',
            align:"center",
            dataIndex: 'recommend'
          },
          {
            title:'设为封面课程 0 不推荐 1推荐',
            align:"center",
            dataIndex: 'banner'
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
          list: "/course/adminCourse/list",
          delete: "/course/adminCourse/delete",
          deleteBatch: "/course/adminCourse/deleteBatch",
          exportXlsUrl: "/course/adminCourse/exportXls",
          importExcelUrl: "course/adminCourse/importExcel",
          
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
        fieldList.push({type:'string',value:'title',text:'title',dictCode:''})
        fieldList.push({type:'string',value:'url',text:'课程地址',dictCode:''})
        fieldList.push({type:'string',value:'image',text:'封面图片',dictCode:''})
        fieldList.push({type:'int',value:'price',text:'价格',dictCode:''})
        fieldList.push({type:'int',value:'type',text:'类型1 -收费,2 免费， 3限时免费',dictCode:''})
        fieldList.push({type:'int',value:'watchNum',text:'观看数量',dictCode:''})
        fieldList.push({type:'int',value:'goodNum',text:'点赞数量',dictCode:''})
        fieldList.push({type:'int',value:'state',text:'标识0-完结,1-正在更新',dictCode:''})
        fieldList.push({type:'int',value:'sort',text:'排序字段',dictCode:''})
        fieldList.push({type:'int',value:'delFlag',text:'删除标识0-正常,1-已删除',dictCode:''})
        fieldList.push({type:'int',value:'courseType',text:'课程类型 1-文章 2-视频',dictCode:''})
        fieldList.push({type:'string',value:'city',text:'城市',dictCode:''})
        fieldList.push({type:'int',value:'starNum',text:'收藏数量',dictCode:''})
        fieldList.push({type:'int',value:'forwardNum',text:'转发数量',dictCode:''})
        fieldList.push({type:'int',value:'contentType',text:'内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目',dictCode:''})
        fieldList.push({type:'int',value:'recommend',text:'设为推荐 0 不推荐 1推荐',dictCode:''})
        fieldList.push({type:'int',value:'banner',text:'设为封面课程 0 不推荐 1推荐',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>