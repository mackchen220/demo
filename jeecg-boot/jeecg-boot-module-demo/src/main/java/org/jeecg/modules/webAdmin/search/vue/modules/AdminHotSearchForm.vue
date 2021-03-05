<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="热搜标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['title', validatorRules.title]" placeholder="请输入热搜标题"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="热搜分类：1 首页热搜 2社区热搜 3朋友圈热搜" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['contentType', validatorRules.contentType]" placeholder="请输入热搜分类：1 首页热搜 2社区热搜 3朋友圈热搜" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="搜索次数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['searchNum', validatorRules.searchNum]" placeholder="请输入搜索次数" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="推荐标识0-正常,1-推荐" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['recommend', validatorRules.recommend]" placeholder="请输入推荐标识0-正常,1-推荐" style="width: 100%" />
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
    name: 'AdminHotSearchForm',
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
          title: {
            rules: [
              { required: true, message: '请输入热搜标题!'},
            ]
          },
          contentType: {
            rules: [
              { required: true, message: '请输入热搜分类：1 首页热搜 2社区热搜 3朋友圈热搜!'},
            ]
          },
          searchNum: {
            rules: [
              { required: true, message: '请输入搜索次数!'},
            ]
          },
          recommend: {
            rules: [
              { required: true, message: '请输入推荐标识0-正常,1-推荐!'},
            ]
          },
          delFlag: {
            rules: [
              { required: true, message: '请输入删除标识0-正常,1-已删除!'},
            ]
          },
        },
        url: {
          add: "/search/adminHotSearch/add",
          edit: "/search/adminHotSearch/edit",
          queryById: "/search/adminHotSearch/queryById"
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
          this.form.setFieldsValue(pick(this.model,'title','contentType','searchNum','recommend','delFlag'))
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
        this.form.setFieldsValue(pick(row,'title','contentType','searchNum','recommend','delFlag'))
      },
    }
  }
</script>