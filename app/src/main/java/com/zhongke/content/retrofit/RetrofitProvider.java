package com.zhongke.content.retrofit;

import com.zhongke.content.HPApplication;
import com.zhongke.content.control.LoginManager;
import com.zhongke.content.entity.ActivityActorBean;
import com.zhongke.content.entity.ActivityDataBean;
import com.zhongke.content.entity.ActivityDetailBean;
import com.zhongke.content.entity.ActivityFlowDataBean;
import com.zhongke.content.entity.ActivityListBean2;
import com.zhongke.content.entity.ActivityRewardBean2;
import com.zhongke.content.entity.ContestantsBean;
import com.zhongke.content.entity.ExaminationQuestion;
import com.zhongke.content.entity.FamilyDetailBean;
import com.zhongke.content.entity.FunctionList_Entity;
import com.zhongke.content.entity.GiftListBean;
import com.zhongke.content.entity.LiveLobbyBean2;
import com.zhongke.content.entity.LivePeopleBean2;
import com.zhongke.content.entity.LoginInfoBean;
import com.zhongke.content.entity.LoginUserInfo;
import com.zhongke.content.entity.MyInfoBean;
import com.zhongke.content.entity.MyJoinActivityBean;
import com.zhongke.content.entity.PartInBean2;
import com.zhongke.content.entity.ProcessBean2;
import com.zhongke.content.entity.PropsBean2;
import com.zhongke.content.entity.RoomInfoBean2;
import com.zhongke.content.entity.UserDesireListBean;
import com.zhongke.content.entity.UserHeartMindListBean;
import com.zhongke.content.entity.WatchPeopleBean;
import com.zhongke.content.entity.WishResponseBean;
import com.zhongke.content.okhttp.OkHttpProvider;
import com.zhongke.content.rxjava.SubscribeUtils;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ${xingen} on 2017/7/10.
 * Retrofit的操作类
 */

public class RetrofitProvider {
//    private final String BASE_URL = "http://yanfayi.cn:8083";

    private final Retrofit retrofit;
    private static RetrofitProvider instance;
    private HapiloService haplioService;

    private RetrofitProvider() {
        OkHttpClient okHttpClient = OkHttpProvider.provideOkHttpClient();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(HttpConstance.BASE_URL)
                .client(okHttpClient)//传输层
                .addConverterFactory(GsonConverterFactory.create())  //Gson解析
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   //Rxjava适配器
                .build();
        this.haplioService = this.retrofit.create(HapiloService.class);
    }

    /**
     * 单例
     *
     * @return
     */
    public static synchronized RetrofitProvider getInstance() {
        if (instance == null) {
            instance = new RetrofitProvider();
        }
        return instance;
    }


    /**
     * 指定创建对应的ServiceInterface.防止基本的Service接口不适合
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createService(final Class<T> service) {
        return this.retrofit.create(service);
    }

    /**
     * 通用方法：将回调接口对象，传递到Subscriber
     *
     * @param subscriber
     * @param <T>
     * @return
     */
    private <T> BaseSubscriber<T> toSubscriber(ResponseResultListener<T> subscriber) {
        return new BaseSubscriber(HPApplication.getContext(), subscriber);
    }


    public Subscription getHomeData(Subscriber<FunctionList_Entity> subscriber) {
        //存放get请求的参数
        Map<String, String> map = new HashMap<>();
        //拼接的地址
        String url = "get_main_config_info";
        return this.haplioService.getHomeData(url, map).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }


    /**
     * 登陆接口
     *
     * @param subscriber 观察者对象
     * @param account    用户名
     * @param pwd        密码
     * @return
     */
    public Subscription login(Subscriber<LoginUserInfo> subscriber, String account, String pwd) {
//    public Observable<LoginInfoBean> login(String account, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("loginName", account);
        map.put("userPass", pwd);
//        return this.haplioService.login(map).flatMap(new Func1<LoginInfoBean, Observable<LoginInfoBean>>() {
//            @Override
//            public Observable<LoginInfoBean> call(LoginInfoBean loginInfoBean) {
//                if (loginInfoBean.getResCode() == 1) {
//                    // 将得到的登陆用户信息保存到配置文件中去
//                    PreferencesUtils.putObject(LoginManager.KEY_LOGIN_INFO, loginInfoBean);
//                } else {
//                    PreferencesUtils.putObject(LoginManager.KEY_LOGIN_INFO, null);
//                }
//                return Observable.just(loginInfoBean);
//            }
//        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);

        Observable<LoginUserInfo> observable = this.haplioService.login(map).flatMap(new Func1<LoginInfoBean, Observable<LoginUserInfo>>() {
            @Override
            public Observable<LoginUserInfo> call(LoginInfoBean loginInfoBean) {
                if (loginInfoBean.getResCode() == 1) {
                    // 将得到的登陆用户信息保存到配置文件中去
                    PreferencesUtils.putObject(LoginManager.KEY_LOGIN_INFO, loginInfoBean);
                    // 设置登录成功之后的信息
                    LoginManager.getInstance().setLoginInfoBean(loginInfoBean);

                    // 再去获取登录用户的详细信息
                    Map<String, String> map = new HashMap<>();
                    map.put("token", loginInfoBean.getModel().getToken());
                    map.put("userId", loginInfoBean.getModel().getUserId() + "");
                    return haplioService.getLoginUserInfo(map);
                } else {
                    PreferencesUtils.putObject(LoginManager.KEY_LOGIN_INFO, null);
                }
                return null;
            }
        });

        return this.haplioService.login(map).flatMap(new Func1<LoginInfoBean, Observable<LoginUserInfo>>() {
            @Override
            public Observable<LoginUserInfo> call(LoginInfoBean loginInfoBean) {
                if (loginInfoBean.getResCode() == 1) {
                    // 将得到的登陆用户信息保存到配置文件中去
                    PreferencesUtils.putObject(LoginManager.KEY_LOGIN_INFO, loginInfoBean);
                    // 设置登录成功之后的信息
                    LoginManager.getInstance().setLoginInfoBean(loginInfoBean);

                    // 再去获取登录用户的详细信息
                    Map<String, String> map = new HashMap<>();
                    map.put("token", loginInfoBean.getModel().getToken());
                    map.put("userId", loginInfoBean.getModel().getUserId() + "");
                    return haplioService.getLoginUserInfo(map);
                } else {
                    PreferencesUtils.putObject(LoginManager.KEY_LOGIN_INFO, null);
                }
                return null;
            }
        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }


    public void login2(Subscriber<LoginUserInfo> subscriber, String account, String pwd){
        Map<String, String> map = new HashMap<>();
        map.put("loginName", account);
        map.put("userPass", pwd);
        this.haplioService.login(map)
                .flatMap(new Func1<LoginInfoBean, Observable<LoginUserInfo>>() {
                    @Override
                    public Observable<LoginUserInfo> call(LoginInfoBean loginInfoBean) {
                        if (loginInfoBean.getResCode() == 1) {
                            LogUtil.i("llj","登录成功---userId-->>>"+loginInfoBean.getModel().getUserId());
                            // 将得到的登陆用户信息保存到配置文件中去
                            PreferencesUtils.putObject(LoginManager.KEY_LOGIN_INFO, loginInfoBean);
                            // 设置登录成功之后的信息
                            LoginManager.getInstance().setLoginInfoBean(loginInfoBean);

                            // 再去获取登录用户的详细信息
                            Map<String, String> map = new HashMap<>();
                            map.put("token", loginInfoBean.getModel().getToken());
                            map.put("userId", loginInfoBean.getModel().getUserId() + "");
                            return haplioService.getLoginUserInfo(map);
                        } else {
                            // 将登录相关信息清掉
                            PreferencesUtils.putObject(LoginManager.KEY_LOGIN_INFO, null);
                            PreferencesUtils.putObject(LoginManager.KEY_LOGIN_USER_INFO,null);
                            LoginManager.getInstance().setLoginInfoBean(null);
                            LoginManager.getInstance().setLoginUserInfo(null);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 获取当前登录用户的详情信息
     *
     * @param subscriber
     * @param userId
     * @param userToken
     * @return
     */
    public Subscription getLoginUserInfo(Subscriber<LoginUserInfo> subscriber, String userId, String userToken) {
        Map<String, String> map = new HashMap<>();
        map.put("token", userToken);
        map.put("userId", userId);
        return this.haplioService.getLoginUserInfo(map).flatMap(new Func1<LoginUserInfo, Observable<LoginUserInfo>>() {
            @Override
            public Observable<LoginUserInfo> call(LoginUserInfo loginUserInfo) {
                if (loginUserInfo.getResCode() == 1) {
                    // 获取用户信息成功
                    PreferencesUtils.putObject(LoginManager.KEY_LOGIN_USER_INFO, loginUserInfo);
                } else {
                    // 获取用户信息失败
                    PreferencesUtils.putObject(LoginManager.KEY_LOGIN_USER_INFO, null);
                }
                return Observable.just(loginUserInfo);
            }
        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    /**
     * 获取抢答列表
     *
     * @param params
     * @param subscriber
     * @return
     */
    public Subscription getRoomList(Map<String, Object> params, ResponseResultListener<RoomInfoBean2> subscriber) {
        return this.haplioService.getRoomList(params).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 查询房间
     */
    public Subscription queryRoom(Map<String,String> map, ResponseResultListener<RoomInfoBean2> subscriber) {
        return this.haplioService.queryRoom(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 获取观战人员列表
     */
    public Subscription watchPeopleList(Map<String,Object> map , ResponseResultListener<WatchPeopleBean> subscriber) {
        return this.haplioService.watchPeopleList(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 点赞
     * userId 被点赞人
     * thumbUpObjectId 因为什么被点赞的对象ID
     * thumbUpType 点赞类型，因为什么被点赞（1.竞赛2.心声）
     */
    public Subscription clickLike(Map<String,String> map,ResponseResultListener subscriber) {
        return this.haplioService.clickLike(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 获取参与人员列表
     */
    public Subscription getParticipants(Map<String,Object> map,ResponseResultListener<PartInBean2> subscriber) {
        return this.haplioService.getParticipants(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 加入房间
     */
    public Subscription joinRoom(Map<String,String> map,ResponseResultListener subscriber) {
        return this.haplioService.joinRoom(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    public Subscription quitRoom(Map<String,Object> map,ResponseResultListener subscriber) {
        return this.haplioService.quitRoom(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 获取直播活动
     */
    public Subscription getLiveActivity(Map<String,Object> map, ResponseResultListener<LiveLobbyBean2> subscriber) {
        return this.haplioService.getLiveActivity(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 获取直播的观看人数
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription getWatchActor(Map<String,Object> map,ResponseResultListener<LivePeopleBean2> subscriber) {
        return this.haplioService.getWatchActor(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 加入直播
     */
    public Subscription joinLive(Map<String,Object> map,ResponseResultListener subscriber) {
        return this.haplioService.joinLive(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 获取活动列表
     */
    public Subscription getActivityList(Map<String,String> map,ResponseResultListener<ActivityListBean2> subscriber) {
        return this.haplioService.getActivityList(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 获取活动奖励列表
     *
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription  getActivityRewardList(Map<String,String> map,ResponseResultListener<ActivityRewardBean2> subscriber) {
        return this.haplioService.getActivityRewardList(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 获取活动参加人员
     */
    public Subscription  getActivityActorList(Map<String,String> map,ResponseResultListener<ActivityActorBean> subscriber) {
        return this.haplioService.getActivityActorList(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 获取活动流程
     */
    public Subscription getActivityProcess(Map<String,String> map , ResponseResultListener<ProcessBean2> subscriber) {
        return this.haplioService.getActivityProcess(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 获取活动道具
     */
    public Subscription getActivityProps(Map<String,String> map , ResponseResultListener<PropsBean2> subscriber) {
        return this.haplioService.getActivityProps(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 获取活动资料
     */
    public Subscription getActivityData(Map<String,String> map , ResponseResultListener<ActivityDataBean> subscriber) {
        return this.haplioService.getActivityData(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }

    /**
     * 获取参加的活动
     */
    public Subscription getMyJoinActivity(Map<String,String> map, ResponseResultListener<MyJoinActivityBean>subscriber) {
        return this.haplioService.getMyJoinActivity(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(subscriber));
    }
    /**
     * 上传语音许愿
     *
     * @param filePath
     * @return
     */
    public Subscription upLoadWishingAudio(String filePath, ResponseResultListener<WishResponseBean> responseResultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("audioUrl", filePath);
        params.put("wishType", 2);
        return haplioService.addUserWish(params).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }
    /**
     * 获取家庭详细资料
     *
     * @param responseResultListener
     * @return
     */
    public Subscription getFamilyDetail(ResponseResultListener<FamilyDetailBean> responseResultListener){

        return haplioService.getMyFamilyDetail().compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }
    /**
     * 获取用户心声列表
     * @param responseResultListener
     * @return
     */
    public Subscription getUserHeartMindList(ResponseResultListener<UserHeartMindListBean> responseResultListener){
        return haplioService.getUserHeartMindList().compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }
    /**
     * 点赞
     * @param responseResultListener
     * @return
     */
    public Subscription sureLikeHeart(Map<String,Object> map,ResponseResultListener responseResultListener){
        return haplioService.sureLikeHeart(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }
    /**
     * 取消点赞
     * @param responseResultListener
     * @return
     */
    public Subscription cancleLikeHeart(Map<String,Object> map,ResponseResultListener responseResultListener){
        return haplioService.cancelLikeHeart(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }

    /**
     * 参加活动
     */
    public Subscription joinActivity(Map<String,Object> map,ResponseResultListener responseResultListener){
        return haplioService.joinActivity(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }

    /**
     * 获取用户愿望列表
     */
    public Subscription getUserDesireList(ResponseResultListener<UserDesireListBean> responseResultListener){
        return haplioService.getUserDesireList().compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }

    /**
     * 获取礼物列表
     */
    public Subscription getGiftList(ResponseResultListener<GiftListBean> responseResultListener){
        return haplioService.getGiftList().compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }

    /**
     * 确定选择礼物或文字许愿
     */
    public Subscription addUeserWish(Map<String,Object> map, ResponseResultListener<WishResponseBean> responseResultListener) {
        return haplioService.addUserWish(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }

    /**
     * 获取考题
     * @param params
     * @param responseResultListener
     * @return
     */
    public Subscription getExaminationQuestion(Map<String,Object> params, ResponseResultListener<ExaminationQuestion> responseResultListener){
        return  haplioService.getExaminationQuestion(params).subscribe(toSubscriber(responseResultListener));
    }

    /**
     * 获取参赛人员
     * @param params
     * @param responseResultListener
     * @return
     */
    public Subscription getContestants(Map<String,Object> params, ResponseResultListener<ContestantsBean> responseResultListener){
        return  haplioService.getContestants(params).subscribe(toSubscriber(responseResultListener));
    }

    /**
     * 获取参赛人员
     * @param params
     * @param responseResultListener
     * @return
     */
    public Subscription commitAnswer(Map<String,Object> params, ResponseResultListener responseResultListener){
        return  haplioService.commitAnswer(params).subscribe(toSubscriber(responseResultListener));
    }

    /**
     * 获取活动详情
     */
    public Subscription getActivityDetail(Map<String,String>map,ResponseResultListener<ActivityDetailBean> responseResultListener){
        return haplioService.getActivityDetail(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }

    /**
     * 获取活动流程数据列表
     */
    public Subscription getActivityComment(Map<String,String>map, ResponseResultListener<ActivityFlowDataBean> responseResultListener){
        return haplioService.getActivityComment(map).compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }


    /**
     * 获取用户数据
     * @param responseResultListener
     * @return
     */
    public Subscription getMyInFo(ResponseResultListener<MyInfoBean> responseResultListener){
        return haplioService.getUserInFo().compose(SubscribeUtils.createTransformer()).subscribe(toSubscriber(responseResultListener));
    }


}
