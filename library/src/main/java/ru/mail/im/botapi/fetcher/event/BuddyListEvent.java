package ru.mail.im.botapi.fetcher.event;

import com.google.gson.annotations.SerializedName;
import ru.mail.im.botapi.entity.Buddy;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuddyListEvent extends Event<BuddyListEvent.Data> {

    @Override
    public <IN, OUT> OUT accept(final EventVisitor<IN, OUT> visitor, final IN in) {
        return visitor.visitBuddyList(this, in);
    }

    @Nonnull
    public List<Buddy> getBuddies() {
        return withData(data -> {
            final List<Buddy> result = new ArrayList<>();
            for (BuddyGroup group : data.groups) {
                result.addAll(group.buddies);
            }
            return result;
        });
    }

    static class Data {
        @SerializedName("groups")
        private List<BuddyGroup> groups = Collections.emptyList();
    }

    private static class BuddyGroup {
        @SerializedName("id")
        private long id;

        @SerializedName("name")
        private String name;

        @SerializedName("buddies")
        private List<Buddy> buddies = Collections.emptyList();
    }
}
