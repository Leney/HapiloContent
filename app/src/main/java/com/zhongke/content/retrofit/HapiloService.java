package com.zhongke.content.retrofit;

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
import com.zhongke.content.entity.UserHeartMindListBean;
import com.zhongke.content.entity.WatchPeopleBean;
import com.zhongke.content.entity.UserDesireListBean;
import com.zhongke.content.entity.WishAwardBean;
import com.zhongke.content.entity.WishResponseBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by ${xingen} on 2017/7/10.
 * <p>
 * 网络请求的Header,Body,Response。
 */

public interface HapiloService {

    @GET
//获取主页数据
    Observable<FunctionList_Entity> getHomeData(@Path("path") String path, @QueryMap Map<String, String> options);

    @GET("/user/login")
    Observable<LoginInfoBean> login(@QueryMap Map<String, String> options);

    @GET("user/get_user_info")
    Observable<LoginUserInfo> getLoginUserInfo(@QueryMap Map<String, String> options);

    /**
     * 获取抢答大厅列表数据
     *
     * @param params
     * @return
     */
    @GET("/contest/get_room_list")
    Observable<HttpResult<RoomInfoBean2>> getRoomList(@QueryMap Map<String, Object> params);

    /**
     * 查询房间
     */
    @GET("/contest/find_room")
    Observable<HttpResult<RoomInfoBean2>> queryRoom(@QueryMap Map<String, String> data);

    /**
     * 观看人员列表
     */
    @GET("/contest/get_look_user_list")
    Observable<HttpResult<WatchPeopleBean>> watchPeopleList(@QueryMap Map<String, Object> params);

    /**
     * 点赞
     */
    @GET("/user/thumb_up")
    Observable<HttpResult> clickLike(@QueryMap Map<String, String> params);

    /**
     * 获取参与人员列表
     */
    @GET("/contest/get_actor_list")
    Observable<HttpResult<PartInBean2>> getParticipants(@QueryMap Map<String, Object> params);

    /**
     * 加入房间
     */
    @GET("/contest/join_room")
    Observable<HttpResult> joinRoom(@QueryMap Map<String, String> params);

    /**
     * 3.18退出房间
     */
    @GET("/contest/quit_room")
    Observable<HttpResult> quitRoom(@QueryMap Map<String, Object> params);

    /**
     * 获取直播活动列表
     *
     * @param data
     * @return
     */
    @GET("/live/get_live_list")
    Observable<HttpResult<LiveLobbyBean2>> getLiveActivity(@QueryMap Map<String, Object> data);

    /**
     * 获取直播观看人数
     *
     * @param data
     * @return
     */
    @GET("/live/get_actor_list")
    Observable<HttpResult<LivePeopleBean2>> getWatchActor(@QueryMap Map<String, Object> data);

    /**
     * 加入直播
     */
    @GET("/live/join_live_room")
    Observable<HttpResult> joinLive(@QueryMap Map<String, Object> data);

    /**
     * 获取活动列表
     */
    @GET("/action/get_happilo_action_list")
    Observable<HttpResult<ActivityListBean2>> getActivityList(@QueryMap Map<String, String> data);

    /**
     * 获取活动奖励列表
     */
    @GET("/action/get_action_award_list")
    Observable<HttpResult<ActivityRewardBean2>> getActivityRewardList(@QueryMap Map<String, String> data);

    /**
     * 获取活动参加人员列表
     */
    @GET("/action/get_actor_list")
    Observable<HttpResult<ActivityActorBean>> getActivityActorList(@QueryMap Map<String, String> data);

    /**
     * 获取活动流程
     */
    @GET("/action/get_action_flow_list")
    Observable<HttpResult<ProcessBean2>> getActivityProcess(@QueryMap Map<String, String> data);

    /**
     * 获取活动道具
     */
    @GET("/action/get_action_tools_list")
    Observable<HttpResult<PropsBean2>> getActivityProps(@QueryMap Map<String, String> data);

    /**
     * 获取活动资料
     */
    @GET("/action/get_action_detail")
    Observable<HttpResult<ActivityDataBean>> getActivityData(@QueryMap Map<String, String> data);

    /**
     * 我的愿望列表
     *
     * @return
     */
    @GET("/wish/get_user_wish_list")
    Observable<HttpResult<UserDesireListBean>> getUserDesireList();

    /**
     * 获取我的参加的活动
     */
    @GET("/action/get_my_join_action")
    Observable<HttpResult<MyJoinActivityBean>> getMyJoinActivity(@QueryMap Map<String, String> data);

    /**
     * 用户许愿
     *
     * @param data
     * @return
     */
    @GET("/wish/add_user_wish")
    Observable<HttpResult<WishResponseBean>> addUserWish(@QueryMap Map<String, Object> data);

    /**
     * 获取愿望及礼物
     */
    @GET("/wish/get_wish_award_list")
    Observable<HttpResult<WishAwardBean>> getWishAwardList(@QueryMap Map<String,Object> data);

    /**
     * 获取愿望列表
     */
    @GET("/wish/get_gift_list")
    Observable<HttpResult<GiftListBean>> getGiftList();

    /**
     * 获取我的家，详细资料
     *
     * @return
     */
    @GET("/group/get_hapilo_my_family_detail")
    Observable<HttpResult<FamilyDetailBean>> getMyFamilyDetail();

    /**
     * 获取心声列表
     *
     * @return
     */
    @POST("/group/get_user_heart_mind_list")
    Observable<HttpResult<UserHeartMindListBean>> getUserHeartMindList();

    /**
     * 提交评论
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/group/comment_heart")
    Observable<HttpResult> commentHeart(@FieldMap Map<String, Object> params);

    /**
     * 进行点赞
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/group/thumb_up_heart")
    Observable<HttpResult> sureLikeHeart(@FieldMap Map<String, Object> params);

    /**
     * 取消点赞
     */
    @FormUrlEncoded
    @POST("/group/cancel_thumb_up_heart")
    Observable<HttpResult> cancelLikeHeart(@FieldMap Map<String, Object> params);

    /**
     * 参加活动
     */
    @GET("/action/join_action")
    Observable<HttpResult> joinActivity(@QueryMap Map<String,Object> params);

    /**
     * 获取答题试卷
     */
   @GET("/contest/get_paper")
   Observable<HttpResult<ExaminationQuestion>> getExaminationQuestion(@QueryMap Map<String,Object> params);

    /**
     * 竞赛人员列表
     */
    @GET("/contest/get_room_user_list")
    Observable<HttpResult<ContestantsBean>> getContestants(@QueryMap Map<String,Object> params);

    /**
     * 提交试卷
     * @param params
     * @return
     */
    @GET("/contest/submit_answer")
    Observable<HttpResult> commitAnswer(@QueryMap Map<String,Object> params);

    /**
     * 获得活动详情
     */
    @GET("/action/get_action_detail")
    Observable<HttpResult<ActivityDetailBean>> getActivityDetail(@QueryMap Map<String,String> params);

    /**
     * 获取活动流程数据列表
     */
    @GET("/action/get_action_evaluate")
    Observable<HttpResult<ActivityFlowDataBean>> getActivityComment(@QueryMap Map<String,String> param);

    /**
     * 获取用户信息
     */
    @GET("/user/get_user_info")
    Observable<HttpResult<MyInfoBean>> getUserInFo();

}
