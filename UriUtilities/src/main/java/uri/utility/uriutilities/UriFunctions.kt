package uri.utility.uriutilities

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.jvm.Throws

object UriFunctions {

    /**
     *This is used to get media art pictures displayed when playing a song.
     * just provide an id from MediaStore
     */
    fun getArt(id : String) : Uri {
        val uriContent = Uri.parse("content://media/external/audio/albumart")
        val art = ContentUris.withAppendedId(uriContent,id.toLong())
        return art
    }


    /**
     * This is used to write bytes to Uris.
     * For file Uri (file:///data/user.....) make sure it is specific to your app or it
     * will throw URI exposed exception.
     */
    fun writeBytesToUri(uri : Uri,byte: ByteArray,context : Context) : Boolean{
        context.contentResolver.openOutputStream(uri).use { outputstream ->
            if(outputstream == null){
                return false
            }else{
                outputstream.write(byte)
                return true
            }
        }
    }

    /**
     * This is used to read bytes from Uris.
     * For file Uri (file:///data/user.....) make sure it is specific to your app or it
     * will throw URI exposed exception.
     * For content Uri(content://com.android.providers....) try to use Storage Access Framework
     * it has access permission to external storage and so it will provide you will Uri.
     */
    fun readBytesFromUri(uri : Uri,context : Context) : ByteArray?{
        context.contentResolver.openInputStream(uri).use {inputstream ->
            if (inputstream == null){
                return null
            }else{
                return inputstream.readBytes()
            }
        }
    }

    /**
     * writes bytes to a private file(in context.filesDir) and
     * returns true if successful and false if not
     */
    fun writeBytesToPrivateFile(filename : String,context: Context,bytes: ByteArray) : Boolean{
        val file = File(context.filesDir,filename)
        try {
            FileOutputStream(file).use { outputstream ->
                outputstream.write(bytes)
            }
            return true
        }catch (exception : Exception){
            return false
        }
    }

    /**
    * read bytes from a file(in context.filesDir) and returns Byte Array object if
     * successful or null if not.
    */
    fun readBytesFromPrivateFile(filename : String,context: Context) : ByteArray?{
        val file = File(context.filesDir,filename)
        try {
            FileInputStream(file).use { outputstream ->
                return outputstream.readBytes()
            }
        }catch (exception : Exception){
            return null
        }
    }
}