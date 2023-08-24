package com.mavericks.daringmates

class Dares {
    var dareId: String? = null
    var dareTitle: String? = null
    var dareDesc: String? = null
    //var dareAssignTo: String? = null
    var dareSendID: String? = null
    var dareReceiverID: String? = null

    constructor(){}

    constructor(dareId: String?, dareTitle: String?, dareDesc: String?, /*dareAssignTo :String?,*/ dareSendID: String?, dareReceiverID: String? ){
        this.dareId = dareId
        this.dareTitle = dareTitle
        this.dareDesc = dareDesc
        //this.dareAssignTo = dareAssignTo
        this.dareSendID = dareSendID
        this.dareReceiverID = dareReceiverID
    }
}