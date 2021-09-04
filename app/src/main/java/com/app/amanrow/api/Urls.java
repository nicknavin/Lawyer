package com.app.amanrow.api;

public interface Urls {



    /*Base URL*/
    String BASE = "http://www.amanrow.com/ServiceItegrity.svc/"; //production
   String BASE_ROLL="http://www.amanrow.com/ServiceLC.svc/";
   String BASE_CLIENT="http://www.amanrow.com/ServiceIIP.svc/";

    /*Common URLS*/
    String LOGIN = BASE + "user_login";
    String GET_USER = BASE + "userDetail";
    String REGISTER = BASE + "userReg";
    String UPDATEUSER = BASE + "userUpdatedetail";
    String CHANGEPASSWORD = BASE + "userchangepwd";
    String UPLOADIMAGE = "http://www.amanrow.com/upload_iip_user_pic.asmx/upload_file";
    String COUNTRY = BASE + "get_country";
    String GET_MASTERDATA = BASE + "get_masterdata";
    String GET_CATEGORY = BASE + "get_category";
    String GET_COURSE = BASE + "get_course";

    String GET_CHAPTER = BASE + "get_chapter";
    String GET_TOPICS_ID = BASE + "get_topics_id";
    String INS_UPD_COURSE = BASE + "ins_upd_course";
    String GET_COURSE_ID = BASE + "get_course_id";
    String INS_UPD_COURSE_CHAPTERS = BASE + "ins_upd_course_chapters";
    String INS_UPD_COURSE_TOPICS = BASE + "ins_upd_course_topics";
    String COURSE_IMAGE = BASE + "course_image/";

    String INS_COURSE_CHAPTERS_TOPICS = BASE + "ins_course_chapters_topics";
    String GET_CHAPTER_TOPICS = BASE + "get_chapter_topics";
    String IMAGEURL="http://sessionway.com/course_image/";
    String UPLOAD_VIDEO="http://sessionway.com/uploadFile.asmx/iip_upload_file";

    /*---------------------------------------------------------------*/
    String ATT_USER_ROLE=BASE_ROLL+"att_user_role";
    String ATT_HEARING_STAF_READ=BASE_ROLL+"att_hearing_staf_read";
    String ATT_CASE_USER_READ=BASE_ROLL+"att_case_user_read";
    String GETPROGRESSREMARK=BASE_ROLL+"getprogressremark";
    String INSERTPROGRESSREMARK=BASE_ROLL+"insertprogressremark";
    String GET_CASE_PROGRESS_DETAIL=BASE_ROLL+"get_case_progress_detail";
    String GET_CASE_PROGRESS_LIST=BASE_ROLL+"get_case_progress_list";
    String GET_LCS_MASTER=BASE_ROLL+"get_lcs_master";
    String CASE_PROGRESS_COM=BASE_ROLL+"case_progress_com";

    /*client*/
    String IIP_MOB_APP_CODE_CHECK=BASE_CLIENT+"iip_mob_app_code_check";
    String GLOBAL_CLIENT_CREDENTIAL=BASE+"global_client_credential";
    String GCLIENT_CASE_READ=BASE_ROLL+"gclient_case_read";





}
