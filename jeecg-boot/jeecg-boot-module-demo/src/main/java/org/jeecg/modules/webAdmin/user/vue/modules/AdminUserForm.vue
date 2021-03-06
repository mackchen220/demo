<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['userName', validatorRules.userName]" placeholder="请输入用户名"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="昵称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['nickName']" placeholder="请输入昵称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="头像" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['headImage']" placeholder="请输入头像"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="VIP等级id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['vipId']" placeholder="请输入VIP等级id" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="个性签名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sign']" placeholder="请输入个性签名"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="可提现余额度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['money', validatorRules.money]" placeholder="请输入可提现余额度" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="删除标识0-正常,1-已删除" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['delFlag', validatorRules.delFlag]" placeholder="请输入删除标识0-正常,1-已删除" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="禁用 1不禁用 0禁用" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['isDisable', validatorRules.isDisable]" placeholder="请输入禁用 1不禁用 0禁用" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="微信号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['wechat']" placeholder="请输入微信号码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="1-用户，2-达人，3-机构，4-平台" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['userType']" placeholder="请输入1-用户，2-达人，3-机构，4-平台" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="代理id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['agencyId', validatorRules.agencyId]" placeholder="请输入代理id" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="登录次数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['loginTimes', validatorRules.loginTimes]" placeholder="请输入登录次数" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="1-男,0-女" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['gender']" placeholder="请输入1-男,0-女" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="出生日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择出生日期" v-decorator="['birthday']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="电话." :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['phone']" placeholder="请输入电话."  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="最后修改密码时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择最后修改密码时间" v-decorator="['updPwdTime', validatorRules.updPwdTime]" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="禁用说明" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['disableRemark']" placeholder="请输入禁用说明"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="邀请码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['inviteCode', validatorRules.inviteCode]" placeholder="请输入邀请码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否达人，0不是 1是" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['isTalent', validatorRules.isTalent]" placeholder="请输入是否达人，0不是 1是" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="省份" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['province']" placeholder="请输入省份"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="城市" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['city']" placeholder="请输入城市"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="绑定微信的id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['weixinId']" placeholder="请输入绑定微信的id"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'AdminUserForm',
    components: {
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          userName: {
            rules: [
              { required: true, message: '请输入用户名!'},
            ]
          },
          money: {
            rules: [
              { required: true, message: '请输入可提现余额度!'},
            ]
          },
          delFlag: {
            rules: [
              { required: true, message: '请输入删除标识0-正常,1-已删除!'},
            ]
          },
          isDisable: {
            rules: [
              { required: true, message: '请输入禁用 1不禁用 0禁用!'},
            ]
          },
          agencyId: {
            rules: [
              { required: true, message: '请输入代理id!'},
            ]
          },
          loginTimes: {
            rules: [
              { required: true, message: '请输入登录次数!'},
            ]
          },
          updPwdTime: {
            rules: [
              { required: true, message: '请输入最后修改密码时间!'},
            ]
          },
          inviteCode: {
            rules: [
              { required: true, message: '请输入邀请码!'},
            ]
          },
          isTalent: {
            rules: [
              { required: true, message: '请输入是否达人，0不是 1是!'},
            ]
          },
        },
        url: {
          add: "/user/adminUser/add",
          edit: "/user/adminUser/edit",
          queryById: "/user/adminUser/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'userName','nickName','headImage','vipId','sign','money','delFlag','isDisable','wechat','userType','agencyId','loginTimes','gender','birthday','phone','updPwdTime','disableRemark','inviteCode','isTalent','province','city','weixinId'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'userName','nickName','headImage','vipId','sign','money','delFlag','isDisable','wechat','userType','agencyId','loginTimes','gender','birthday','phone','updPwdTime','disableRemark','inviteCode','isTalent','province','city','weixinId'))
      },
    }
  }
</script>