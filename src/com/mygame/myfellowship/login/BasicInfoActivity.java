//package com.mygame.myfellowship.login;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.renderscript.Sampler;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//import cn.smssdk.framework.utils.Data;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.mygame.myfellowship.BaseActivity;
//import com.mygame.myfellowship.FriendListActivity;
//import com.mygame.myfellowship.Question;
//import com.mygame.myfellowship.R;
//import com.mygame.myfellowship.bean.CfgCommonType;
//import com.mygame.myfellowship.bean.Constant;
//import com.mygame.myfellowship.bean.Response;
//import com.mygame.myfellowship.bean.Urls;
//import com.mygame.myfellowship.gps.MyLocation;
//import com.mygame.myfellowship.http.AjaxCallBack;
//import com.mygame.myfellowship.http.AjaxParams;
//import com.mygame.myfellowship.struct.StructBaseUserInfo;
//import com.mygame.myfellowship.struct.StructFriendListShowContent;
//import com.mygame.myfellowship.utils.AssetUtils;
//import com.mygame.myfellowship.utils.CharacterParse;
//import com.mygame.myfellowship.utils.ToastHelper;
//import com.mygame.myfellowship.utils.WheelViewUtil;
//import com.mygame.myfellowship.utils.WheelViewUtil.OnCfgWheelListener;
//import com.mygame.myfellowship.utils.WheelViewUtil.OnWheelViewListener;
//
//public class BasicInfoActivity extends BaseActivity {
//
//	RadioGroup group;
//	LinearLayout mLinearLayoutCheckBox, llContainer;
//	CheckBox CheckBox1;
//	CheckBox CheckBox2;
//	CheckBox CheckBox3;
//	CheckBox CheckBox4;
//	CheckBox CheckBox5;
//	CheckBox CheckBox6;
//	CheckBox CheckBox7;
//	TextView tvQuestion;
//	Button btnChoose;
//	private List<Question> requestList;
//	List<CfgCommonType> highCcts = new ArrayList<CfgCommonType>();
//	private int currentQId;
//	
//	final int HANDLE_BASETOPIC = 0x1001;//基本信息
//	final int HANDLE_MBTI = 0x1002;//MBTI性格测试
//	final int HANDLE_SPARE_TIME = 0x1003;//空余时间
//	final int HANDLE_LOCATION = 0x1004;//坐标
//	int mSpareTime = 0;
//	CharacterParse mCharacterParse;
//	private int questionType = 1;//题目类型 1、代表课选择的基本信息 2、MBTI性格测试题  3、空余时间  4  、坐标 5、完成
//	private int mMBTIbigType = 0;
//	//用户基本信息结构体
//	StructBaseUserInfo mStructBaseUserInfo = new StructBaseUserInfo();
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.question_container);
//		mStructBaseUserInfo.setUserid(preferences.getString(Constant.USER_ID, ""));
//		mStructBaseUserInfo.setNickname(preferences.getString(Constant.NICK_NAME, ""));
//		setTitle("答题");
//		mCharacterParse = new CharacterParse();
//		initView();
//		initData();
//		requestBasicTopic();
//	}
//	
//	
//	private void initData() {
//		for(int i = 0; i < 50; i++){
//			CfgCommonType cfg = new CfgCommonType();
//			cfg.setName("" + (150 + i));
//			highCcts.add(cfg);
//		}
//	}
//
//	
//	private void requestBasicTopic() {
//		 String t = AssetUtils.getDataFromAssets(this, "question.txt");
//		 questionType = 1;
//		 parseBasicTopic(t);
//	}
//
//	//解析题目json
//	private int parseBasicTopic(String t) {
//		int result = 0;
//		Response<List<Question>> response = new Gson().fromJson(t, 
//				new TypeToken<Response<List<Question>>>(){}.getType());
//		if(response.getResult()){
//			requestList = response.getResponse();
//			currentQId = 0;
//			Message msg = new Message();
//			msg.what = HANDLE_BASETOPIC;
//			msg.arg1 = currentQId;
//			mHandler.sendMessage(msg);
//		}else{
//			result = -1;
//			ToastHelper.ToastLg(response.getMessage(), getActivity());
//		}
//		return result;
//	}
//
//
//	private void initView() {
//		
//		 group = (RadioGroup)findViewById(R.id.rgroup);
//		 mLinearLayoutCheckBox = (LinearLayout)findViewById(R.id.LinearLayoutCheckBox);
//		 llContainer = (LinearLayout)findViewById(R.id.llContainer);
//		 CheckBox1 = (CheckBox)findViewById(R.id.CheckBox1);
//		 CheckBox2 = (CheckBox)findViewById(R.id.CheckBox2);
//		 CheckBox3 = (CheckBox)findViewById(R.id.CheckBox3);
//		 CheckBox4 = (CheckBox)findViewById(R.id.CheckBox4);
//		 CheckBox5 = (CheckBox)findViewById(R.id.CheckBox5);
//		 CheckBox6= (CheckBox)findViewById(R.id.CheckBox6);
//		 CheckBox7 = (CheckBox)findViewById(R.id.CheckBox7);
//		 CheckBox1.setOnCheckedChangeListener(mOnCheckedListener);
//		 CheckBox2.setOnCheckedChangeListener(mOnCheckedListener);
//		 CheckBox3.setOnCheckedChangeListener(mOnCheckedListener);
//		 CheckBox4.setOnCheckedChangeListener(mOnCheckedListener);
//		 CheckBox5.setOnCheckedChangeListener(mOnCheckedListener);
//		 CheckBox6.setOnCheckedChangeListener(mOnCheckedListener);
//		 CheckBox7.setOnCheckedChangeListener(mOnCheckedListener);
//		 tvQuestion = (TextView)findViewById(R.id.tvQuestion);
//		 btnChoose = (Button)findViewById(R.id.btnClickChoose);
//		 
//		 addRightBtn(R.string.next_topic, new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//
//				//基本可选择信息填写
//				if(questionType == 1){
//					int checkId = group.getCheckedRadioButtonId();
//					
//					if(currentQId < requestList.size() - 1){
//						// 跳过身高和年龄
//						if(requestList.get(currentQId).getQuesionId().equals("0002")
//								|| requestList.get(currentQId).getQuesionId().equals("0003")){
//							if(btnChoose.getText().toString().equals(requestList.get(currentQId).getQuestion())){
//								ToastHelper.ToastLg("没有选择"+requestList.get(currentQId).getQuestion(), getApplicationContext());
//								return;
//							}
//						} else if(checkId <= 0){
//							ToastHelper.ToastLg("没有选择答案", getApplicationContext());
//							return;
//						}
//						saveAnsers(checkId-1);
//						currentQId ++;
//						Message msg = new Message();
//						msg.what = HANDLE_BASETOPIC;
//						msg.arg1 = currentQId;
//						mHandler.sendMessage(msg);	
//					}else{
//						showTestEMBI();
//					}
//
//				} 
//				else if(questionType == 2){
//					int checkId = group.getCheckedRadioButtonId();
//					if(checkId <= 0){
//						ToastHelper.ToastLg("没有选择答案", getApplicationContext());
//						return;
//					}
//					if(currentQId < requestList.size() - 1){
//						saveAnsers(checkId-1);
//						currentQId ++;
//						Message msg = new Message();
//						msg.what = HANDLE_BASETOPIC;
//						msg.arg1 = currentQId;
//						mHandler.sendMessage(msg);	
//					}else{
//						mMBTIbigType = mCharacterParse.getCharacterType();
//						mStructBaseUserInfo.setMBTI(mMBTIbigType+"");
//						preferences.edit().putString(Constant.Nature,mMBTIbigType+"").commit();
//						Message msg = new Message();
//						msg.what = HANDLE_SPARE_TIME;
//						mHandler.sendMessage(msg);	
//					}
//						
//				}
//				else if(questionType == 3){
////						mStructBaseUserInfo.setSpareTime(Integer.toString(mSpareTime));
//						preferences.edit().putString(Constant.Freetime,Integer.toString(mSpareTime)).commit();
//						Message msg = new Message();
//						msg.what = HANDLE_LOCATION;
//						mHandler.sendMessage(msg);	
//				}
//				else if(questionType == 4){
//					SubmitAllUserInfo();
//				}
//				
//			}
//		});
//	}
//
//
//	/**
//	 * 保存答案
//	 * @param checkId
//	 */
//	protected void saveAnsers(int checkId) {
//		Question curQ = requestList.get(currentQId);
//		String qId = curQ.getQuesionId();
//		if("0001".equals(qId)){ // 性别
//			preferences.edit().putString(Constant.Sex,curQ.getAnswerstype().get(checkId)).commit();
//			mStructBaseUserInfo.setSex(curQ.getAnswerstype().get(checkId));
//		} else if("0002".equals(qId)){ // 年龄
////			preferences.edit().putString(Constant.Age,curQ.getAnswerstype().get(checkId)).commit();
////			mStructBaseUserInfo.setAge(curQ.getAnswerstype().get(checkId));
//			preferences.edit().putString(Constant.Age,btnChoose.getText().toString()).commit();
//			mStructBaseUserInfo.setBirthday(btnChoose.getText().toString());
//		} else if("0003".equals(qId)){ // 身高
////			preferences.edit().putString(Constant.Height, curQ.getAnswerstype().get(checkId)).commit();
////			mStructBaseUserInfo.setStature(curQ.getAnswerstype().get(checkId));
//			preferences.edit().putString(Constant.Height, btnChoose.getText().toString()).commit();
//			mStructBaseUserInfo.setStature(btnChoose.getText().toString());
//		} else if("0004".equals(qId)){ // 小孩
//			preferences.edit().putString(Constant.IfChild, curQ.getAnswerstype().get(checkId)).commit();
//			mStructBaseUserInfo.setIfHaveChildren(curQ.getAnswerstype().get(checkId));
//		} else if("0005".equals(qId)){ // 匹配对象有孩子?
//			preferences.edit().putString(Constant.IfMind, curQ.getAnswerstype().get(checkId)).commit();
//			mStructBaseUserInfo.setIfMindHaveChildren(curQ.getAnswerstype().get(checkId));
//		} else if("0006".equals(qId)){ // 物质要求
//			preferences.edit().putString(Constant.ThingAsk, curQ.getAnswerstype().get(checkId)).commit();
//			mStructBaseUserInfo.setSubstanceNeeds(curQ.getAnswerstype().get(checkId));
//		} else if("0007".equals(qId)){ // 谈恋爱时间期限
//			preferences.edit().putString(Constant.MarryNum, curQ.getAnswerstype().get(checkId)).commit();
//			mStructBaseUserInfo.setInLovePeriod(curQ.getAnswerstype().get(checkId));
//		} else if("0008".equals(qId)){ // 你看好中国的经济吗
//			preferences.edit().putString(Constant.Faith1, curQ.getAnswerstype().get(checkId)).commit();
//		} else if("0009".equals(qId)){
//			preferences.edit().putString(Constant.Faith2, curQ.getAnswerstype().get(checkId)).commit();
//		} else if("0010".equals(qId)){
//			// 读取faith1，faith2，去三个最多的
//			preferences.edit().putString(Constant.Faith3, curQ.getAnswerstype().get(checkId)).commit();
//			String Faith1 = preferences.getString(Constant.Faith1, "A");
//			String Faith2 = preferences.getString(Constant.Faith2, "A");
//			String Faith3 = preferences.getString(Constant.Faith3, "A");
//			String FaithType = getFaithType(Faith1, Faith2, Faith3);
//			preferences.edit().putString(Constant.Faith, FaithType).commit();
//			mStructBaseUserInfo.setFaith(FaithType);
//		} 
//		// qId以1开头的，都可以算作MBAI性格测试题 .. 后续继续保存数据，并计算性格测试结果
//		if(questionType == 2){
//			int typeInt = mCharacterParse.MTBITypeToInt(curQ.getAnswerstype().get(checkId));
//			mCharacterParse.setCharacterAndNum(typeInt);
//		}
//	}
//	//计算信仰类型
//	String getFaithType(String Faith1,String Faith2,String Faith3){
//		int a = 0;
//		int b = 0;
//		if(Faith1.equals("A")){
//			a++;
//		}else{
//			b++;
//		}
//		if(Faith2.equals("A")){
//			a++;
//		}else{
//			b++;
//		}
//		if(Faith3.equals("A")){
//			a++;
//		}else{
//			b++;
//		}
//		return (a>b)?"A":"B";
//	}
//	protected void requestMBAIQuestion() {
//		AjaxParams params = new AjaxParams();
//		params.put("userid", preferences.getString(Constant.USER_ID, "adcd"));
//		getFinalHttp().post(Urls.question_info,params, new AjaxCallBack<String>(){
//
//			@Override
//			public void onStart() {
//				super.onStart();
//				showReqeustDialog(R.string.requestion_topic);
//			}
//
//			@Override
//			public void onSuccess(String t) {
//				super.onSuccess(t);
//				
//				if(parseBasicTopic(t) == 0){
//					questionType = 2;
//				}
//				cancelRequestDialog();
//			}
//
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				super.onFailure(t, errorNo, strMsg);
//				cancelRequestDialog();
//			}
//			
//		});
//	}
//
//
//	protected void showTestEMBI() {
//		 new AlertDialog.Builder(this).setTitle("提示")
//		 .setMessage("基本信息答完，继续测试MBAI?")
//		 .setPositiveButton("前往", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				requestMBAIQuestion();
//				cancelRequestDialog(); 
//			}
//		}).create().show();
//	}
//
//
//	@SuppressLint("HandlerLeak")
//	Handler mHandler = new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg); 
//			int curId = msg.what;
//			switch (curId) {
//			case HANDLE_BASETOPIC:
//				BasicInfoRadioGroupView(msg.arg1);
//				break;
//			case HANDLE_SPARE_TIME:
//				SpareTimeView();
//				break;
//			case HANDLE_LOCATION:
//				questionType = 4;
//				List<String> coordinates = new ArrayList<String>();
//				coordinates.add(Double.toString(myLocation.getLongitude()));
//				coordinates.add(Double.toString(myLocation.getLatitude()));
//				mStructBaseUserInfo.setCoordinates(coordinates);
//				Set<String> siteno = new HashSet<String>(); 
//				for(int i=0;i<coordinates.size();i++){
//					siteno.add(coordinates.get(i));
//				}
//				preferences.edit().putStringSet(Constant.Address,siteno).commit();
//				SubmitAllUserInfo();
//				break;
//			default:
//				break;
//			}
//			
//		}
//		
//	};
//	 
//	@Override
//	protected void onDestroy() {
//		super.onDestroy(); 
//	}
//	
//	OnWheelViewListener ageListener = new OnWheelViewListener() {
//		
//		@Override
//		public void doubleConfirm(String selectLeft, String selectRight) {
//			 
//		}
//		
//		@Override
//		public void dateConfirm(String selectyear, String selectmonth,
//				String selectday, int year, int month, int day) {
//			 btnChoose.setText(selectyear + "-" + selectmonth + "-" + selectday);
//		}
//		
//		@Override
//		public void Confirm(String select, int index) {
//		}
//	};
//	
//	
//	OnCfgWheelListener highListener = new OnCfgWheelListener() {
//		
//		@Override
//		public void CustomSalayConfirm(String min, String max) {
//			 
//		}
//		@Override
//		public void Confirm(CfgCommonType select, int index) {
//			btnChoose.setText(select.getName());
//		}
//	};
//	public String getNowYear() {
//		   Date currentTime = new Date();
//		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		   String dateString = formatter.format(currentTime);
//		   return dateString;
//	}
//	//下一个题目显示
//	protected void BasicInfoRadioGroupView(int curId) {
//		final Question q = requestList.get(curId);
//		int id = 0;
//		
//		if("0002".equals(q.getQuesionId())
//		|| "0003".equals(q.getQuesionId())) {
//			tvQuestion.setVisibility(View.GONE);
//			group.setVisibility(View.GONE);
//			llContainer.setVisibility(View.VISIBLE);
//			btnChoose.setText(q.getQuestion());
//			btnChoose.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					if("0002".equals(q.getQuesionId())){
//						WheelViewUtil.showWheelView(getActivity(), v, ageListener, getNowYear(), "选择年龄", false);
//					} else if("0003".equals(q.getQuesionId())) {
//						WheelViewUtil.showSingleWheel(getActivity(), v, highCcts, highListener, "选择身高", "身高");
//					}
//				}
//			});
//		} else {
//			tvQuestion.setVisibility(View.VISIBLE);
//			group.setVisibility(View.VISIBLE);
//			llContainer.setVisibility(View.GONE);
//			group.removeAllViews();
//			group.clearCheck();
//			tvQuestion.setText(q.getQuestion());
//			for(String answer : q.getAnswers()){
//				RadioButton rBtn = (RadioButton) View.inflate(this, R.layout.radio_button, null);
//				rBtn.setId(id+1);
//				rBtn.setText(answer);
//				group.addView(rBtn);
//				id++;
//			}
//		}
//	}
//	
//	
//	//测试空余时间试题
//	protected void SpareTimeView() {
//		questionType = 3;
//		group.setVisibility(View.GONE);
//		mLinearLayoutCheckBox.setVisibility(View.VISIBLE);
//		tvQuestion.setText("你的空余时间有哪些？");
//	}
//	
//	OnCheckedChangeListener mOnCheckedListener = new OnCheckedChangeListener() {
//		
//		@Override
//		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//			// TODO Auto-generated method stub
//			buttonView.getId();
//			switch (buttonView.getId()) {
//			case R.id.CheckBox1:
//				mSpareTime = mSpareTime | (1<<0);
//				break;
//			case R.id.CheckBox2:
//				mSpareTime = mSpareTime | (1<<1);
//				break;
//			case R.id.CheckBox3:
//				mSpareTime = mSpareTime | (1<<2);
//				break;
//			case R.id.CheckBox4:
//				mSpareTime = mSpareTime | (1<<3);
//				break;
//			case R.id.CheckBox5:
//				mSpareTime = mSpareTime | (1<<4);
//				break;
//			case R.id.CheckBox6:
//				mSpareTime = mSpareTime | (1<<5);
//				break;
//			case R.id.CheckBox7:
//				mSpareTime = mSpareTime | (1<<6);
//				break;
//
//			default:
//				break;
//			}
//		}
//	};
//	void testJson(){
//		StructBaseUserInfo mStructBaseUserInfo = new StructBaseUserInfo();
//		mStructBaseUserInfo.setBirthday("12");
//		mStructBaseUserInfo.setFaith("dfd");
//		List<String> coordinates = new ArrayList<String>();
//		coordinates.add("24.56");
//		coordinates.add("108.2325");
//		mStructBaseUserInfo.setCoordinates(coordinates);
//		
//		String getjson = new Gson().toJson(mStructBaseUserInfo);
//		Log.i("huwei", getjson);
//	}
//}
//
