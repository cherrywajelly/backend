package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupResponses;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IconGroupServiceTest implements IconGroupService {

    @Override
    public void buyIconGroup(long memberId, long iconGroupId) {

    }

    @Override
    public List<IconGroupResponses> getIconGroups(final long memberId) {
        List<IconGroupResponses> iconGroupResponses = new ArrayList<>();

        List<IconResponse> iconResponses = new ArrayList<>();
        iconResponses.add(new IconResponse(1, "iconUrl"));

        iconGroupResponses.add(new IconGroupResponses(1, "name", iconResponses));
        return iconGroupResponses;
    }
}