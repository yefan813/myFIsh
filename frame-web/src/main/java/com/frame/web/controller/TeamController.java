package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.Team;
import com.frame.domain.UserTeamRelation;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.img.ImageValidate;
import com.frame.domain.img.ImgDealMsg;
import com.frame.domain.img.Result;
import com.frame.domain.vo.TeamVO;
import com.frame.service.ImgSysService;
import com.frame.service.TeamService;
import com.frame.service.UserTeamRelationService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/team")
public class TeamController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

	@Resource
	private TeamService teamService;

	@Resource
	private ImgSysService imgSysService;
	
	@Resource
	private UserTeamRelationService userTeamRelationService;

	@RequestMapping(value = "/getAllTeams", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getAllTeams(Page<Team> page) {
		RemoteResult result = null;
		try {
			Team query = new Team();
			query.setYn(YnEnum.Normal.getKey());
			Page<Team> teams = teamService.selectPage(query, page);
			// TODO image 加入前缀
			result = RemoteResult.result(BusinessCode.SUCCESS, teams.getResult());
		} catch (Exception e) {
			LOGGER.error("列表异常", e);
			System.out.println("列表异常" + e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/searchTeamByName", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String searchTeamByName(String name, String cityCode) {
		RemoteResult result = null;
		if (StringUtils.isEmpty(name)) {
			LOGGER.error("调用searchTeamByName 传入参数为：" + name);
			result = RemoteResult.result(BusinessCode.PARAMETERS_ERROR);
			return JSON.toJSONString(result);
		}
		try {

			List<Team> res = teamService.searchTeamByName(name,cityCode);
			if (null != res) {
				result = RemoteResult.success(res);
			} else {
				result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
			}

		} catch (Exception e) {
			LOGGER.error("列表异常", e);
			System.out.println("列表异常" + e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/deletePlayer", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String deletePlayer(Long teamId, Long userId) {
		RemoteResult result = null;
		if (teamId == null || teamId < 0 ||  userId == null || userId < 0) {
			LOGGER.error("调用deletePlayer 传入参数为：teamId" + teamId + "----> userId" + userId);
			result = RemoteResult.result(BusinessCode.PARAMETERS_ERROR);
			return JSON.toJSONString(result);
		}
		try {
			
			UserTeamRelation query = new UserTeamRelation();
			query.setTeamId(teamId);
			query.setUserId(userId);
			query.setYn(YnEnum.Normal.getKey());
			
			List<UserTeamRelation> uRelations = userTeamRelationService.selectEntryList(query);
			if(CollectionUtils.isEmpty(uRelations)){
				LOGGER.error("调用deletePlayer未找到对此用户的球队");
				LOGGER.error("调用deletePlayer 传入参数为：teamId" + teamId + "----> userId" + userId);
				result = RemoteResult.result(BusinessCode.NO_MATCH_DATA);
				return JSON.toJSONString(result);
			}
			userTeamRelationService.deleteByKey(uRelations.get(0).getId().longValue());
			result = RemoteResult.success();
			return JSON.toJSONString(result);
		} catch (Exception e) {
			LOGGER.error("删除球队成员异常", e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/getTeamById", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getTeamById(Long teamId) {
		RemoteResult result = null;
		if (teamId == null || teamId < 0) {
			LOGGER.error("调用getTeamById 传入参数为：" + teamId);
			result = RemoteResult.result(BusinessCode.PARAMETERS_ERROR);
			return JSON.toJSONString(result);
		}
		try {

			TeamVO res = teamService.getTeamById(teamId);
			if (null != res) {
				result = RemoteResult.success(res);
			} else {
				result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
			}

		} catch (Exception e) {
			LOGGER.error("列表异常", e);
			System.out.println("列表异常" + e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/getUserTeams/{userId}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getUserTeams(@PathVariable Long userId) {

		RemoteResult result = null;
		if (null == userId || userId < 0) {
			LOGGER.error("调用getUserTeams 传入参数为：" + userId);
			result = RemoteResult.result(BusinessCode.PARAMETERS_ERROR);
			return JSON.toJSONString(result);
		}
		try {
			List<Team> teams = teamService.getUserTeams(userId);
			// TODO image 加入前缀
			result = RemoteResult.result(BusinessCode.SUCCESS, teams);
		} catch (Exception e) {
			LOGGER.error("列表异常", e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		}

		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/createTeam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String createTeam(@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "userName") String userName, @RequestParam(value = "name") String name,
			@RequestParam(value = "peopleCount") Integer peopleCount,
			@RequestParam(value = "teamDescription") String teamDesc,
			@RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
		RemoteResult result = null;
		Team team = new Team();
		try {
			if (null == userId || StringUtils.isEmpty(userName) || StringUtils.isEmpty(name) || null == peopleCount) {
				LOGGER.error("调用createTeam 传入的参数错误 userId【{}】,userName[{}] , name[{}],peopleCount码[{}],验证时间【{}】",
						userId, userName, name, peopleCount);
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}

			if (imgFile != null && imgFile.getSize() > 0) {
				try {
					if (imgFile.getBytes() != null && imgFile.getBytes().length > 0) {
						Result r = ImageValidate.validate4Upload(imgFile);
						if (r.isSuccess()) {
							ImgDealMsg re = imgSysService.uploadByteImg(imgFile.getBytes(), "lanqiupai");
							if (re != null && re.isSuccess()) {
								// 上传成功
								String imgUrl = (String) re.getMsg();
								// 上床成功设置template 图片路径
								team.setImgUrl(imgUrl);
							} else {
								// 上传文件失败，在页面提示
								result = RemoteResult.failure("0001", "球队头像上传失败！");
								return dealJosnP("", result);
							}
						} else {
							result = RemoteResult.failure("0001", r.getResultCode());
							return dealJosnP("", result);
						}
					}
				} catch (Exception e) {
					LOGGER.error("失败:" + e.getMessage(), e);
					result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
				}
			}
			team.setCreateUser(userId);
			team.setCreateUserName(userName);
			team.setName(name);
			team.setPeopleCount(peopleCount);
			team.setLostTimes(0);
			team.setWinTimes(0);
			team.setCurrentCount(1);
			team.setStatus(Team.TEAMSTATUS_NOTFULL);
			team.setYn(YnEnum.Normal.getKey());
			if (null == userId || userId < 0) {
				LOGGER.error("调用createTeam 传入参数为：" + userId + "  team:" + team);
				result = RemoteResult.result(BusinessCode.PARAMETERS_ERROR);
				return JSON.toJSONString(result);
			}
			if (null != team) {
				result = teamService.createTeam(userId, team);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	
	@RequestMapping(value = "/editTeam", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String editTeam(Team team,
			@RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
		RemoteResult result = null;
		try{
		if (null == team || team.getId() == null) {
			LOGGER.info("调用editTeam 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		Team querTeam = teamService.selectEntry(team.getId().longValue());
		if(querTeam == null){
			LOGGER.info("未找到编辑的相关球队");
			result = RemoteResult.failure("0001", "未找到编辑的相关球队");
			return JSON.toJSONString(result);
		}
		String imgUrl = null;
		if (imgFile != null && imgFile.getSize() > 0) {
			try {
				if (imgFile.getBytes() != null && imgFile.getBytes().length > 0) {
					Result r = ImageValidate.validate4Upload(imgFile);
					if (r.isSuccess()) {
						ImgDealMsg re = imgSysService.uploadByteImg(imgFile.getBytes(), "lanqiupai");
						if (re != null && re.isSuccess()) {
							// 上传成功
							imgUrl = (String) re.getMsg();
						} else {
							// 上传文件失败，在页面提示
							result = RemoteResult.failure("0001", "头像上传失败！");
							return dealJosnP("", result);
						}
					} else {
						result = RemoteResult.failure("0001", r.getResultCode());
						return dealJosnP("", result);
					}
				}
			} catch (Exception e) {
				LOGGER.error("失败:" + e.getMessage(), e);
				result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
			}
		}
		
		Team updateTeam = new Team();
		updateTeam.setId(team.getId());
		if(StringUtils.isNotEmpty(team.getName())){
			updateTeam.setName(team.getName());
		}
		if(team.getPeopleCount() != null){
			updateTeam.setPeopleCount(team.getPeopleCount());
		}
		if(StringUtils.isNotEmpty(team.getTeamDesc())){
			updateTeam.setTeamDesc(team.getTeamDesc());
		}
		if(StringUtils.isNotEmpty(imgUrl)){
			updateTeam.setImgUrl(imgUrl);
		}
		
		if(teamService.updateByKey(updateTeam) > 0){
			result = RemoteResult.success();
		}else{
			result = RemoteResult.failure("0001", "");
		}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
}
