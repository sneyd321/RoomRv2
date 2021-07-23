package com.sneydr.roomrv2.App.Validation;

import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.ContactsContract;

import com.sneydr.roomrv2.App.ConnectionManager;
import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;


public class IntentFactory {

    public Intent getCalendarIntent() {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, "Upcoming Rent");
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);
        return intent;
    }

    public Intent getDocumentIntent(Document document) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        connectionManager.setLeaseUrl(document.getDocumentURL());
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, document.getName());
        return intent;
    }

    public Intent getGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        return intent;
    }

    public Intent getContactsIntent(Tenant tenant) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, tenant.getEmail())
                .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .putExtra(ContactsContract.Intents.Insert.NAME, tenant.getFullName())
                .putExtra(ContactsContract.Intents.Insert.PHONE, tenant.getPhoneNumber())
                .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        return intent;
    }

}
