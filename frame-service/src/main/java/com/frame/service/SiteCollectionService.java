package com.frame.service;

import com.frame.domain.ArticalCollection;
import com.frame.domain.FishSite;
import com.frame.domain.common.Page;
import com.frame.domain.vo.SiteCollectionVO;
import com.frame.service.base.BaseService;
import com.frame.domain.SiteCollection;



/**
 * 
 * SiteCollectionService业务层接口类
 * @author yefan
 * @date 2017-11-29 17:01:39
 **/

public interface SiteCollectionService extends BaseService<SiteCollection , Long> {


    Page<FishSite> getSiteCollectionById(SiteCollectionVO siteCollectionVO, Page<SiteCollection> page);
}