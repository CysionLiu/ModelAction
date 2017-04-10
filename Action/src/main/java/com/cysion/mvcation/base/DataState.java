package com.cysion.mvcation.base;

/**
 * Created by CysionLiu on 2017/4/7.
 * Cache data operator
 */

public enum DataState {

    /*
     *
     * Never use cache,always from network
     */
    NO_CACHE,


    /*  read from cache first,if there is no in cache ,request network,then
    write the data into cache
    */
    CACHE_FIRST,


    /*request from network first,if gotten,refresh cache,if no,read from cache;if cache is empty,fail*/
    NET_FIRST,


    /* pull down and refresh data,request from network,if gotten,refresh cache,if no,fail
           * --only the target core data gotten is successful*/
    HEAD_REFRESH,



    /* load more data,the defalut implemetation is the same as MO_CACHE
         * --only the target core data gotten is successful*/
    LOAD_MORE
}
