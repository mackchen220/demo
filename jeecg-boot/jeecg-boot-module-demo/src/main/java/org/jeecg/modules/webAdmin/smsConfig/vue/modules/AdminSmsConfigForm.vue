<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="短信接口地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sendApi', validatorRules.sendApi]" placeholder="请输入短信接口地址"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="接口账号 (必填)（企业登录名）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['loginName', validatorRules.loginName]" placeholder="请输入接口账号 (必填)（企业登录名）"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="短信内容模板" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sendContent', validatorRules.sendContent]" placeholder="请输入短信内容模板"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="密码 (必填)（企业账号对应密码）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['password', validatorRules.password]" placeholder="请输入密码 (必填)（企业账号对应密码）"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="签名（可为空）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['signName']" placeholder="请输入签名（可为空）"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="计费套餐类型 (必填)2 行业套餐 3 为政务套餐" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['feeType', validatorRules.feeType]" placeholder="请输入计费套餐类型 (必填)2 行业套餐 3 为政务套餐"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="删除标识0-正常,1-已删除" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['delFlag', validatorRules.delFlag]" placeholder="请输入删除标识0-正常,1-已删除" style="width: 100%" />
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
    name: 'AdminSmsConfigForm',
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
          sendApi: {
            rules: [
              { required: true, message: '请输入短信接口地址!'},
            ]
          },
          loginName: {
            rules: [
              { required: true, message: '请输入接口账号 (必填)（企业登录名）!'},
            ]
          },
          sendContent: {
            rules: [
              { required: true, message: '请输入短信内容模板!'},
            ]
          },
          password: {
            rules: [
              { required: true, message: '请输入密码 (必填)（企业账号对应密码）!'},
            ]
          },
          feeType: {
            rules: [
              { required: true, message: '请输入计费套餐类型 (必填)2 行业套餐 3 为政务套餐!'},
            ]
          },
          delFlag: {
            rules: [
              { required: true, message: '请输入删除标识0-正常,1-已删除!'},
            ]
          },
        },
        url: {
          add: "/smsConfig/adminSmsConfig/add",
          edit: "/smsConfig/adminSmsConfig/edit",
          queryById: "/smsConfig/adminSmsConfig/queryById"
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
          this.form.setFieldsValue(pick(this.model,'sendApi','loginName','sendContent','password','signName','feeType','delFlag'))
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
        this.form.setFieldsValue(pick(row,'sendApi','loginName','sendContent','password','signName','feeType','delFlag'))
      },
    }
  }
</script>