package lht.wangtong.gowin120.patient.net;

import io.reactivex.Observable;
import lht.wangtong.gowin120.patient.bean.DataResponse;
import lht.wangtong.gowin120.patient.db.Token;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by luoyc on 2018/3/5.
 */

public interface ApiService {
//    /**
//     * 知识体系
//     * http://www.wanandroid.com/tree/json
//     *
//     * @return BannerResponse
//     */
//    @GET("/tree/json")
//    Observable<DataResponse<List<KnowledgeSystem>>> getKnowledgeSystems();
//
//    /**
//     * 知识体系下的文章
//     * http://www.wanandroid.com/article/list/0/json?cid=168
//     *
//     * @param page page
//     * @param cid  cid
//     */
//    @GET("/article/list/{page}/json")
//    Observable<DataResponse<Article>> getKnowledgeSystemArticles(@Path("page") int page, @Query("cid") int cid);
//
//
//    /**
//     * 常用网站
//     * http://www.wanandroid.com/friend/json
//     */
//    @GET("/friend/json")
//    Observable<DataResponse<List<Friend>>> getHotFriends();
//
//    /**
//     * 大家都在搜
//     * http://www.wanandroid.com/hotkey/json
//     */
//    @GET("/hotkey/json")
//    Observable<DataResponse<List<HotKey>>> getHotKeys();
//
//    /**
//     * 搜索
//     * http://www.wanandroid.com/article/query/0/json
//     *
//     * @param page page
//     * @param k    POST search key
//     */
//    @POST("/article/query/{page}/json")
//    @FormUrlEncoded
//    Observable<DataResponse<Article>> getSearchArticles(@Path("page") int page, @Field("k") String k);
//
//
//    /**
//     * 登录
//     *
//     * @param username username
//     * @param password password
//     * @return Deferred<User>
//     */
//    @POST("/user/login")
//    @FormUrlEncoded
//    Observable<DataResponse<User>> login(@Field("username") String username, @Field("password") String password);
//
//    /**
//     * 注册
//     *
//     * @param username   username
//     * @param password   password
//     * @param repassword repassword
//     * @return Deferred<User>
//     */
//    @POST("/user/register")
//    @FormUrlEncoded
//    Observable<DataResponse<User>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);
//
//    /**
//     * 收藏文章
//     *
//     * @param id id
//     * @return Deferred<DataResponse>
//     */
//    @POST("/lg/collect/{id}/json")
//    Observable<DataResponse> addCollectArticle(@Path("id") int id);
//
//    /**
//     * 收藏站外文章
//     *
//     * @param title  title
//     * @param author author
//     * @param link   link
//     * @return Deferred<DataResponse>
//     */
//    @POST("/lg/collect/add/json")
//    @FormUrlEncoded
//    Observable<DataResponse> addCollectOutsideArticle(@Field("title") String title, @Field("author") String author, @Field("link") String link);
//
//    /**
//     * 删除收藏文章
//     *
//     * @param id       id
//     * @param originId -1
//     * @return Deferred<DataResponse>
//     */
//    @POST("/lg/uncollect/{id}/json")
//    @FormUrlEncoded
//    Observable<DataResponse> removeCollectArticle(@Path("id") int id, @Field("originId") int originId);
//

    /**
     * 获取当前token
     * @param md5
     * @param time
     * @return
     */
    @POST("applyToken.do")
    @FormUrlEncoded
    Observable<DataResponse<Token>> getToken(@Field("accessFlag")String accessFlag,@Field("md5") String md5, @Field("time") String time);

}
