package top.gsc.shareservice.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.gsc.shareservice.mapper.ShareMapper;
import top.gsc.shareservice.model.entity.Share;
import top.gsc.shareservice.model.entity.User;
import top.gsc.shareservice.model.vo.ShareVO;
import top.gsc.shareservice.model.vo.UserVO;
import top.gsc.shareservice.openfeign.UserService;

@Slf4j
@RestController
public class ShareController {
    @Autowired
    private ShareMapper shareMapper;
    @Resource
    private UserService userService;

    @GetMapping("/share")
    public ShareVO getShare(@RequestParam Long id) {
        Share share = shareMapper.selectById(id);
        if (share == null) {
            return null;
        }
        User user = userService.getUser(share.getUserId()).getData();
        if (user == null) {
            return null;
        }
        ShareVO shareVO = new ShareVO();
        shareVO.setId(share.getId());
        shareVO.setUserId(share.getUserId());
        shareVO.setTitle(share.getTitle());
        shareVO.setIsOriginal(share.getIsOriginal());
        shareVO.setAuthor(share.getAuthor());
        shareVO.setCover(share.getCover());
        shareVO.setSummary(share.getSummary());
        shareVO.setPrice(share.getPrice());
        shareVO.setDownloadUrl(share.getDownloadUrl());
        shareVO.setBuyCount(share.getBuyCount());
        shareVO.setShowFlag(share.getShowFlag());
        shareVO.setAuditStatus(share.getAuditStatus());
        shareVO.setReason(share.getReason());
        shareVO.setCreateTime(share.getCreateTime());
        shareVO.setUpdateTime(share.getUpdateTime());

        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUserName(user.getUsername());
        userVO.setAvatarUrl(user.getAvatarUrl());
        shareVO.setUser(userVO);

        return  shareVO;
    }
}
