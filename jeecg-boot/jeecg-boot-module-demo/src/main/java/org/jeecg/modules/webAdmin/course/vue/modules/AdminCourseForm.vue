<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="title" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['title', validatorRules.title]" placeholder="请输入title"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="课程地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['url', validatorRules.url]" placeholder="请输入课程地址"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="封面图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple  v-decorator="['image', validatorRules.image]" ></j-image-upload>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="价格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['price']" placeholder="请输入价格" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="类型1 -收费,2 免费， 3限时免费" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['type', validatorRules.type]" :trigger-change="true" dictCode="" placeholder="请选择类型1 -收费,2 免费， 3限时免费" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="观看数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['watchNum']" placeholder="请输入观看数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="点赞数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['goodNum']" placeholder="请输入点赞数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="标识0-完结,1-正在更新" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['state', validatorRules.state]" placeholder="请输入标识0-完结,1-正在更新" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="排序字段" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['sort']" placeholder="请输入排序字段" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="删除标识0-正常,1-已删除" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['delFlag', validatorRules.delFlag]" placeholder="请输入删除标识0-正常,1-已删除" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="课程类型 1-文章 2-视频" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['courseType', validatorRules.courseType]" placeholder="请输入课程类型 1-文章 2-视频" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="城市" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['city']" placeholder="请输入城市"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收藏数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['starNum']" placeholder="请输入收藏数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="转发数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['forwardNum']" placeholder="请输入转发数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['contentType']" placeholder="请输入内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="设为推荐 0 不推荐 1推荐" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['recommend']" placeholder="请输入设为推荐 0 不推荐 1推荐" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="设为封面课程 0 不推荐 1推荐" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['banner']" placeholder="请输入设为封面课程 0 不推荐 1推荐" style="width: 100%" />
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
    name: 'AdminCourseForm',
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
              { required: true, message: '请输入title!'},
            ]
          },
          url: {
            rules: [
              { required: true, message: '请输入课程地址!'},
            ]
          },
          image: {
            rules: [
              { required: true, message: '请输入封面图片!'},
            ]
          },
          type: {
            rules: [
              { required: true, message: '请输入类型1 -收费,2 免费， 3限时免费!'},
            ]
          },
          state: {
            rules: [
              { required: true, message: '请输入标识0-完结,1-正在更新!'},
            ]
          },
          delFlag: {
            rules: [
              { required: true, message: '请输入删除标识0-正常,1-已删除!'},
            ]
          },
          courseType: {
            rules: [
              { required: true, message: '请输入课程类型 1-文章 2-视频!'},
            ]
          },
        },
        url: {
          add: "/course/adminCourse/add",
          edit: "/course/adminCourse/edit",
          queryById: "/course/adminCourse/queryById"
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
          this.form.setFieldsValue(pick(this.model,'title','url','image','price','type','watchNum','goodNum','state','sort','delFlag','courseType','city','starNum','forwardNum','contentType','recommend','banner'))
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
        this.form.setFieldsValue(pick(row,'title','url','image','price','type','watchNum','goodNum','state','sort','delFlag','courseType','city','starNum','forwardNum','contentType','recommend','banner'))
      },
    }
  }
</script>