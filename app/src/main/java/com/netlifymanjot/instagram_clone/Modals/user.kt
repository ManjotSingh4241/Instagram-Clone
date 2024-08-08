package com.netlifymanjot.instagram_clone.Modals

class user {
    var image:String?=null
    var name:String?=null
    var eamil:String?=null
    var password:String?=null
    constructor()
    constructor(image: String?, name: String?, eamil: String?, password: String?) {
        this.image = image
        this.name = name
        this.eamil = eamil
        this.password = password
    }

    constructor(name: String?, eamil: String?, password: String?) {
        this.name = name
        this.eamil = eamil
        this.password = password
    }

    constructor(eamil: String?, password: String?) {
        this.eamil = eamil
        this.password = password
    }


}