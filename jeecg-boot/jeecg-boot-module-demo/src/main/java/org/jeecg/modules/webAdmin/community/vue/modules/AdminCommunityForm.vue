<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="userId" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['userId', validatorRules.userId]" placeholder="请输入userId"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['content']" placeholder="请输入内容"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="链接" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['url']" placeholder="请输入链接"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="图片或者视频地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple  v-decorator="['imageUrl']" ></j-image-upload>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="定位城市" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['city']" placeholder="请输入定位城市"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="朋友圈类型:1 照片 2视频 3课程  4其他  " :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['type']" placeholder="请输入朋友圈类型:1 照片 2视频 3课程  4其他  " style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="1-用户，2-达人，3-机构，4-平台" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['userType']" placeholder="请输入1-用户，2-达人，3-机构，4-平台" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收藏数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['starNum']" placeholder="请输入收藏数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="点赞数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['goodNum']" placeholder="请输入点赞数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="转发数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['forwardNum']" placeholder="请输入转发数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="观看数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['watchNum']" placeholder="请输入观看数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="审核状态 0待审核 1 审核未通过2审核通过" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['checkStatus']" placeholder="请输入审核状态 0待审核 1 审核未通过2审核通过" style="width: 100%" />
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
    name: 'AdminCommunityForm',
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
          userId: {
            rules: [
              { required: true, message: '请输入userId!'},
            ]
          },
        },
        url: {
          add: "/community/adminCommunity/add",
          edit: "/community/adminCommunity/edit",
          queryById: "/community/adminCommunity/queryById"
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
          this.form.setFieldsValue(pick(this.model,'userId','content','url','imageUrl','city','type','userType','starNum','goodNum','forwardNum','watchNum','checkStatus'))
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
        this.form.setFieldsValue(pick(row,'userId','content','url','imageUrl','city','type','userType','starNum','goodNum','forwardNum','watchNum','checkStatus'))
      },
    }
  }
</script>