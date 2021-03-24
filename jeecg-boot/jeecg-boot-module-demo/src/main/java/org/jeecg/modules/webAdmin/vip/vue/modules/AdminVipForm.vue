<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="会员名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['vipName', validatorRules.vipName]" placeholder="请输入会员名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="限时价格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['priceLow']" placeholder="请输入限时价格" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="原价格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['priceHigh']" placeholder="请输入原价格" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="佣金百分比，例：百分之五十，数据为50" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['commission']" placeholder="请输入佣金百分比，例：百分之五十，数据为50" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="奖金区间小" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['bonusHigh']" placeholder="请输入奖金区间小" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="奖金区间大" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['bonusLow']" placeholder="请输入奖金区间大" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="整形折扣百分比 例：五折，数据为50" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['discount']" placeholder="请输入整形折扣百分比 例：五折，数据为50" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="旅游次数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['times']" placeholder="请输入旅游次数" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="区域分红百分比" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['areaCommission']" placeholder="请输入区域分红百分比" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="限时名额数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['quotaNum']" placeholder="请输入限时名额数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择开始时间" v-decorator="['beginTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择结束日期" v-decorator="['endTime']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="删除标识0-正常,1-已删除" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['delFlag', validatorRules.delFlag]" placeholder="请输入删除标识0-正常,1-已删除" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="vip卡图标" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple  v-decorator="['image']" ></j-image-upload>
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
    name: 'AdminVipForm',
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
          vipName: {
            rules: [
              { required: true, message: '请输入会员名称!'},
            ]
          },
          delFlag: {
            rules: [
              { required: true, message: '请输入删除标识0-正常,1-已删除!'},
            ]
          },
        },
        url: {
          add: "/vip/adminVip/add",
          edit: "/vip/adminVip/edit",
          queryById: "/vip/adminVip/queryById"
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
          this.form.setFieldsValue(pick(this.model,'vipName','priceLow','priceHigh','commission','bonusHigh','bonusLow','discount','times','areaCommission','quotaNum','beginTime','endTime','delFlag','image'))
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
        this.form.setFieldsValue(pick(row,'vipName','priceLow','priceHigh','commission','bonusHigh','bonusLow','discount','times','areaCommission','quotaNum','beginTime','endTime','delFlag','image'))
      },
    }
  }
</script>