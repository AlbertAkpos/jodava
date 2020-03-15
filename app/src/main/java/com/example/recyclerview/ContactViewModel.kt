package com.example.recyclerview

import androidx.lifecycle.ViewModel

class ContactViewModel: ViewModel() {
    var contacts = ContactRepository.get().getContacts()
}