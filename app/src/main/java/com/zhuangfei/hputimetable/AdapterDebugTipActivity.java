package com.zhuangfei.hputimetable;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhuangfei.hputimetable.api.TimetableRequest;
import com.zhuangfei.hputimetable.api.model.AdapterDebugModel;
import com.zhuangfei.hputimetable.api.model.CheckModel;
import com.zhuangfei.hputimetable.api.model.ObjResult;
import com.zhuangfei.hputimetable.api.model.UserDebugModel;
import com.zhuangfei.toolkit.model.BundleModel;
import com.zhuangfei.toolkit.tools.ActivityTools;
import com.zhuangfei.toolkit.tools.ShareTools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDebugTipActivity extends AppCompatActivity {

    @BindView(R.id.id_name_edittext)
    public EditText nameEdit;

    @BindView(R.id.id_userkey_edittext)
    public EditText userkeydit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_debug_tip);
        ButterKnife.bind(this);
        if(isHasLocal()){
            ActivityTools.toActivity(AdapterDebugTipActivity.this,
                    AdapterDebugListActivity.class);
        }
    }

    @OnClick(R.id.cv_login)
    public void onAdapterBtnClicked() {
        final String name = nameEdit.getText().toString();
        final String userkey = userkeydit.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(userkey)) {
            Toasty.warning(this, "不允许为空，请填充完整!").show();
        } else {
            TimetableRequest.getUserInfo(this, name, userkey,
                    new Callback<ObjResult<UserDebugModel>>() {
                        @Override
                        public void onResponse(Call<ObjResult<UserDebugModel>> call, Response<ObjResult<UserDebugModel>> response) {
                            ObjResult<UserDebugModel> result=response.body();
                            if(result!=null){
                                if(result.getCode()==200){
                                    saveToLocal(name,userkey);
                                    ActivityTools.toActivity(AdapterDebugTipActivity.this,
                                            AdapterDebugListActivity.class);
                                }else{
                                    clearLocal();
                                    Toasty.error(AdapterDebugTipActivity.this,result.getMsg()).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ObjResult<UserDebugModel>> call, Throwable t) {
                            Toasty.error(AdapterDebugTipActivity.this,t.getMessage()).show();
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @OnClick(R.id.ib_back)
    public void goBack() {
        ActivityTools.toBackActivityAnim(this, MainActivity.class);
    }

    boolean isHasLocal(){
        String name=ShareTools.getString(this,"debug_name",null);
        String userkey=ShareTools.getString(this,"debug_userkey",null);
        if(name!=null&&userkey!=null) return true;
        return false;
    }

    void clearLocal(){
        ShareTools.putString(this,"debug_name",null);
        ShareTools.putString(this,"debug_userkey",null);
    }

    void saveToLocal(String name,String userkey){
        ShareTools.putString(this,"debug_name",name);
        ShareTools.putString(this,"debug_userkey",userkey);
    }
}
