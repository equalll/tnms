package com.tigercel.tnms.web;

import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.dto.RouterGroupNodeBean;
import com.tigercel.tnms.model.router.HFDevRouterGroup;
import com.tigercel.tnms.model.router.HFDevRouterNode;
import com.tigercel.tnms.service.DevRouterGroupNodeService;
import com.tigercel.tnms.service.DevRouterGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by freedom on 2016/5/25.
 */
@Controller
@RequestMapping("device/ap/group/node")
public class DevRouterGroupNodeController {

    @Autowired
    private DevRouterGroupNodeService deviceRouterGroupNodeService;

    @Autowired
    private DevRouterGroupService deviceRouterGroupService;

    @RequestMapping(value = "", method = GET)
    public String index(Model model,
                        @RequestParam(value="groupName", required = false) String groupName) {

        if(groupName != null && groupName.length() != 0) {
            HFDevRouterGroup group = deviceRouterGroupService.findOneByName(groupName);
            if(group != null) {
                model.addAttribute("group", group);
            }
        }

        model.addAttribute("menu", "menuGroupNode");

        return "device/ap/group/node/index";
    }

    @RequestMapping(value = "", method = POST)
    public String add(Model model, RouterGroupNodeBean node,
                      @RequestParam(value = "groupName", required = true) String groupName) {

        if(deviceRouterGroupNodeService.isMACValid(node.getStart(), node.getEnd()) == false) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "mac range is not valid");
        }
        else {
            if(deviceRouterGroupNodeService.save(node, groupName) == null) {
                model.addAttribute("result", "error");
            }
            else {
                model.addAttribute("result", "success");
            }
        }

        return "jsonTemplate";
    }

    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "groupName", required = false) String groupName,
                       PageBean pb, Model model) {

        Page<HFDevRouterNode> nodes = null;
        HFDevRouterNode node = null;



        if(groupName != null && groupName.length() != 0 &&
                (search == null || search.length() == 0)) {
            HFDevRouterGroup group = deviceRouterGroupService.findOneByName(groupName);
            if(group != null) {
                nodes = deviceRouterGroupNodeService.findAllByGroup(pb, group);
                setGroupName(nodes);
                model.addAttribute("total", nodes.getTotalElements());
                model.addAttribute("rows", nodes.getContent());
            }
        }
        else if(search != null && search.length() != 0) {
            node = deviceRouterGroupNodeService.findAllByMac(search);
            if(node == null) {
                model.addAttribute("total", 0);
                model.addAttribute("rows", "");
            }
            else {
                node.setGroupName(node.getGroup().getGroupName());
                ArrayList<HFDevRouterNode> tmp = new ArrayList<>();
                tmp.add(node);
                model.addAttribute("total", 1);
                model.addAttribute("rows", tmp);
            }
        }
        else {
            nodes = deviceRouterGroupNodeService.findAll(pb);
            setGroupName(nodes);

            model.addAttribute("total", nodes.getTotalElements());
            model.addAttribute("rows", nodes.getContent());
        }

        model.addAttribute("result", "success");
        return "jsonTemplate";
    }

    @RequestMapping(value = "delete", method = POST)
    public String delete(@RequestParam(value = "id", required = true) long id,
                         Model model) {

        deviceRouterGroupNodeService.delete(id);

        model.addAttribute("result", "success");
        return "jsonTemplate";
    }

    @RequestMapping(value = "g", method = GET)
    public String getGroupId(Model model,
                             @RequestParam(value = "mac", required = true) String mac) {

        HFDevRouterGroup group = deviceRouterGroupNodeService.findGroupByMAC(mac);

        if(group == null) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "not found");
        }
        else {
            model.addAttribute("result", "success");
            model.addAttribute("groupId", group.getGroupId() == null ? "" : group.getGroupId());
        }
        return "jsonTemplate";
    }

    private void setGroupName(Page<HFDevRouterNode> nodes) {
        for(HFDevRouterNode node : nodes.getContent()) {
            if(node.getGroup() != null) {
                node.setGroupName(node.getGroup().getGroupName());
            }
        }
    }
}


