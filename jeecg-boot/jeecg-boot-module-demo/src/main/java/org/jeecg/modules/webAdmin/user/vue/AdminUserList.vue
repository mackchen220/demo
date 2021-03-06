<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="用户名">
              <a-input placeholder="请输入用户名" v-model="queryParam.userName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="昵称">
              <a-input placeholder="请输入昵称" v-model="queryParam.nickName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="邀请码">
                <a-input placeholder="请输入邀请码" v-model="queryParam.inviteCode"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('tb_user')">导出</a-button>
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

    <admin-user-modal ref="modalForm" @ok="modalFormOk"></admin-user-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AdminUserModal from './modules/AdminUserModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'AdminUserList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      AdminUserModal
    },
    data () {
      return {
        description: 'tb_user管理页面',
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
            title:'用户名',
            align:"center",
            dataIndex: 'userName'
          },
          {
            title:'昵称',
            align:"center",
            dataIndex: 'nickName'
          },
          {
            title:'头像',
            align:"center",
            dataIndex: 'headImage'
          },
          {
            title:'VIP等级id',
            align:"center",
            dataIndex: 'vipId'
          },
          {
            title:'个性签名',
            align:"center",
            dataIndex: 'sign'
          },
          {
            title:'可提现余额度',
            align:"center",
            dataIndex: 'money'
          },
          {
            title:'删除标识0-正常,1-已删除',
            align:"center",
            dataIndex: 'delFlag'
          },
          {
            title:'禁用 1不禁用 0禁用',
            align:"center",
            dataIndex: 'isDisable'
          },
          {
            title:'微信号码',
            align:"center",
            dataIndex: 'wechat'
          },
          {
            title:'1-用户，2-达人，3-机构，4-平台',
            align:"center",
            dataIndex: 'userType'
          },
          {
            title:'代理id',
            align:"center",
            dataIndex: 'agencyId'
          },
          {
            title:'登录次数',
            align:"center",
            dataIndex: 'loginTimes'
          },
          {
            title:'1-男,0-女',
            align:"center",
            dataIndex: 'gender'
          },
          {
            title:'出生日期',
            align:"center",
            dataIndex: 'birthday',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'电话.',
            align:"center",
            dataIndex: 'phone'
          },
          {
            title:'最后修改密码时间',
            align:"center",
            dataIndex: 'updPwdTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'禁用说明',
            align:"center",
            dataIndex: 'disableRemark'
          },
          {
            title:'邀请码',
            align:"center",
            dataIndex: 'inviteCode'
          },
          {
            title:'是否达人，0不是 1是',
            align:"center",
            dataIndex: 'isTalent'
          },
          {
            title:'省份',
            align:"center",
            dataIndex: 'province'
          },
          {
            title:'城市',
            align:"center",
            dataIndex: 'city'
          },
          {
            title:'绑定微信的id',
            align:"center",
            dataIndex: 'weixinId'
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
          list: "/user/adminUser/list",
          delete: "/user/adminUser/delete",
          deleteBatch: "/user/adminUser/deleteBatch",
          exportXlsUrl: "/user/adminUser/exportXls",
          importExcelUrl: "user/adminUser/importExcel",
          
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
        fieldList.push({type:'string',value:'userName',text:'用户名',dictCode:''})
        fieldList.push({type:'string',value:'nickName',text:'昵称',dictCode:''})
        fieldList.push({type:'string',value:'headImage',text:'头像',dictCode:''})
        fieldList.push({type:'int',value:'vipId',text:'VIP等级id',dictCode:''})
        fieldList.push({type:'string',value:'sign',text:'个性签名',dictCode:''})
        fieldList.push({type:'int',value:'money',text:'可提现余额度',dictCode:''})
        fieldList.push({type:'int',value:'delFlag',text:'删除标识0-正常,1-已删除',dictCode:'del_flag'})
        fieldList.push({type:'int',value:'isDisable',text:'禁用 1不禁用 0禁用',dictCode:''})
        fieldList.push({type:'string',value:'wechat',text:'微信号码',dictCode:''})
        fieldList.push({type:'int',value:'userType',text:'1-用户，2-达人，3-机构，4-平台',dictCode:''})
        fieldList.push({type:'int',value:'agencyId',text:'代理id',dictCode:''})
        fieldList.push({type:'int',value:'loginTimes',text:'登录次数',dictCode:''})
        fieldList.push({type:'int',value:'gender',text:'1-男,0-女',dictCode:''})
        fieldList.push({type:'date',value:'birthday',text:'出生日期'})
        fieldList.push({type:'string',value:'phone',text:'电话.',dictCode:''})
        fieldList.push({type:'date',value:'updPwdTime',text:'最后修改密码时间'})
        fieldList.push({type:'string',value:'disableRemark',text:'禁用说明',dictCode:''})
        fieldList.push({type:'string',value:'inviteCode',text:'邀请码',dictCode:''})
        fieldList.push({type:'int',value:'isTalent',text:'是否达人，0不是 1是',dictCode:''})
        fieldList.push({type:'string',value:'province',text:'省份',dictCode:''})
        fieldList.push({type:'string',value:'city',text:'城市',dictCode:''})
        fieldList.push({type:'string',value:'weixinId',text:'绑定微信的id',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>