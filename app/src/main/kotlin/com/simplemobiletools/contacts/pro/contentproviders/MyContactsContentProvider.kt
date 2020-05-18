package com.simplemobiletools.contacts.pro.contentproviders

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.simplemobiletools.commons.helpers.MyContactsContentProvider
import com.simplemobiletools.contacts.pro.extensions.config
import com.simplemobiletools.contacts.pro.helpers.LocalContactsHelper

class MyContactsContentProvider : ContentProvider() {
    override fun insert(uri: Uri, contentValues: ContentValues?) = null

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        if (context == null || !context!!.config.showPrivateContacts) {
            return null
        } else {
            val matrixCursor = MatrixCursor(arrayOf(
                MyContactsContentProvider.COL_RAW_ID,
                MyContactsContentProvider.COL_CONTACT_ID,
                MyContactsContentProvider.COL_NAME,
                MyContactsContentProvider.COL_PHOTO_URI,
                MyContactsContentProvider.COL_PHONE_NUMBER)
            )

            LocalContactsHelper(context!!).getPrivateSimpleContactsSync().forEach {
                matrixCursor.newRow()
                    .add(MyContactsContentProvider.COL_RAW_ID, it.rawId)
                    .add(MyContactsContentProvider.COL_CONTACT_ID, it.contactId)
                    .add(MyContactsContentProvider.COL_NAME, it.name)
                    .add(MyContactsContentProvider.COL_PHOTO_URI, it.photoUri)
                    .add(MyContactsContentProvider.COL_PHONE_NUMBER, it.phoneNumber)
            }

            return matrixCursor
        }
    }

    override fun onCreate() = true

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?) = 1

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun getType(uri: Uri) = ""
}
