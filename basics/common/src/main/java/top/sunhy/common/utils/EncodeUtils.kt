package top.sunhy.common.utils

import java.security.MessageDigest

object EncodeUtils {

    private fun toHex(byteArray: ByteArray): String {
        return with(StringBuilder()) {
            //转为字符串
            byteArray.forEach {
                var value = it
                var hex = value.toInt() and (0xFF)
                val toHexString = Integer.toHexString(hex)
                if (toHexString.length == 1) {
                    this.append("0$toHexString")
                } else {
                    this.append(toHexString)
                }
            }
            return this.toString()
        }

    }

    fun sha256(intput: String): String {
        val instance = MessageDigest.getInstance("SHA-256")
        val digest = instance.digest(intput.toByteArray())
        return toHex(digest)
    }

}
