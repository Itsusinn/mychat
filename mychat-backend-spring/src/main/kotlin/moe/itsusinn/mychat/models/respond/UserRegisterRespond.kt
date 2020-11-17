package moe.itsusinn.mychat.models.respond

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "用户注册响应类")
data class UserRegisterRespond(

    @ApiModelProperty(value = "状态", example = "Failed,Success")
    val status: String,
    @ApiModelProperty(value = "message user id or err message")
    val msg: String
)

