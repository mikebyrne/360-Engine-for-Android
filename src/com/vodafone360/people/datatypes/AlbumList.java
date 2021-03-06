/*
 * CDDL HEADER START
 *
 *@author MyZenPlanet Inc.
 *
 * The contents of this file are subject.
 * to the terms of the Common Development and Distribution.
 * License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license.
 * at src/com/vodafone/people/VODAFONE.LICENSE.txt or.
 * See the License for the specific language governing.
 * permissions and limitations under th.
 * License.
 *
 * When distributing Covered Code, include this CDDL HEADER.
 * in each file and include the License.
 * file at src/com/vodafone/people/VODAFONE.LICENSE.txt.
 * If applicable, add the following below this CDDL HEADER,.
 * with the fields enclosed by brackets.
 * "[]" replaced with your own identifying information:.
 * Portions Copyright [yyyy] [name of.
 * copyright owner]
 *
 * CDDL HEADER END
 *
 * Copyright 2009 Vodafone Sales & Services Ltd.  All rights reserved.
 * Use is subject to license terms.
 */
package com.vodafone360.people.datatypes;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import android.os.Parcel;
import android.os.Parcelable;

import com.vodafone360.people.utils.LogUtils;

/**
 * Class for getting list of albums form server.
 *
 * @author mayank
 *
 */
public class AlbumList extends BaseDataType implements Parcelable {
    /**
     * Name for identifying class.
     */
    public static final String NAME = "AlbumList";
    /**
     * items.
     */
    public Integer items;
    /**
     * albumlist.
     */
    public List<Album> albumlist;
    /**
     * updated.
     */
    public Long updated;

    /**
     * constructor for initialising.
     */
    public AlbumList() {
        albumlist = new Vector<Album>();
    }

    /**
     * Constructor creating Tags item for specified String.
     *
     * @param s
     *            String value for Tags item.
     */
    private enum Tags {
        /**
         * albumlist.
         */
        ALBUMLIST("albumlist"),
        /**
         * items.
         */
        ITEMS("items"),
        /**
         * updated.
         */
        UPDATED("updated");

        /**
         * tag.
         */
        private final String tag;

        /**
         * Constructor creating Tags item for specified String.
         *
         * @param s
         *            String value for Tags item.
         */
        private Tags(final String s) {
            tag = s;
        }

        /**
         * String value associated with Tags item.
         *
         * @return String value for Tags item.
         */
        private String tag() {
            return tag;
        }
    }

    @Override
    public final int getType() {
        return ALBUM_LIST;
    }

    /**
     *
     * @param tag
     *            for input
     * @return tags as output
     */
    private Tags findTag(final String tag) {
        for (Tags tags : Tags.values()) {
            if (tag.compareTo(tags.tag()) == 0) {
                return tags;
            }
        }
        return null;
    }

    /**
     *
     * @param hash
     *            table from which we create values.
     */

    public final void createFromHashtable(
            final Hashtable<String, Object> hash) {
        Enumeration<String> e = hash.keys();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            Object value = hash.get(key);
            Tags tag = findTag(key);
            if (tag != null) {
                setValue(tag, value);
            } else {
                LogUtils.logD("Unhandled Key = " + key + "value = "
                        + value.toString());
            }
        }

        return;
    }

    /**
     *
     * @param tag
     *            for setting
     * @param value
     *            object
     */
    private void setValue(final Tags tag,
            final Object value) {
        switch (tag) {

        case ALBUMLIST:
            Vector<Object> v = (Vector<Object>) value;
            for (Object obj : v) {
                Album album = new Album();
                Hashtable<String, Object> hash = (Hashtable<String, Object>) obj;
                album.createFromHashtable(hash);
                albumlist.add(album);
            }
            break;
        case ITEMS:
            items = (Integer) value;
            break;
        case UPDATED:
            updated = (Long) value;
            break;

        default:
            LogUtils.logW("setValue: Unknown tag - " + tag + "[" + value + "]");
        }
    }

    @Override
    public final int describeContents() {
        return 1;
    }

    /** {@inheritDoc} */
    @Override
    public final void writeToParcel(final Parcel dest, final int flags) {

    }

}