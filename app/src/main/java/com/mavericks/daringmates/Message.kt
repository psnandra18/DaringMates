package com.mavericks.daringmates

class Message {
    var message: String? = null
    var senderId: String? = null
    var dare: Dares? = null

    constructor(){}

    constructor(message: String, senderId: String?){
        this.message = message
        this.senderId = senderId
    }

    fun isDareMessage(): Boolean {
        return dare != null
    }
}