package com.frame.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeletePersonApplyDate {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeletePersonApplyDate.class);

	/*@Resource
	private MatchApplyService matchApplyService;

	
	public void work() {
		LOGGER.info("start delete person out date apply");
		List<MatchApply> matchApplies = matchApplyService.selectPersionOutDateApply();
		deletePersonApply(matchApplies);
		LOGGER.info("delete person out date apply Done .......");
	}

	private void deletePersonApply(List<MatchApply> matchApplies){
		if(CollectionUtils.isEmpty(matchApplies)){
			LOGGER.error("delete person outdate apply pass invalid parammeters");
			return;
		}
		try {
			for (MatchApply matchApply : matchApplies) {
				matchApply.setYn(YnEnum.Deleted.getKey());
				
				matchApplyService.updateByKey(matchApply);
			}
			
		} catch (Exception e) {
			LOGGER.error("delete person out date apply occur a Exception", e);
		}
	}
	*/
}
