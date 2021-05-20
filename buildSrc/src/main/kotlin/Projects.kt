import `fun`.kotlingang.deploy.Deploy
import `fun`.kotlingang.deploy.DeployEntity
import `fun`.kotlingang.deploy.DeployProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.util.GUtil.loadProperties
import java.io.File

/**
 * Enabling maven publish task for library.
 * if `deploy.properties` exists in project folder.
 * deploy.properties should have next fields: host(server remote address), user (sftp user on remote server),
 * password (user's password), destination (destination folder path on remote server).
 */
fun Project.applyDeploy() {
    val deployPropertiesFile: File = project.file("deploy.properties")

    if (deployPropertiesFile.exists()) {
        val properties = loadProperties(deployPropertiesFile)

        project.apply<Deploy>()
        project.configure<DeployProperties> {
            username = properties.getProperty("user")
            host = properties.getProperty("host")
            password = properties.getProperty("password")
            deployPath = properties.getProperty("destination")
        }

        project.configure<DeployEntity> {
            group = AppInfo.PACKAGE
            artifactId = AppInfo.ARTIFACT_ID
            version = AppInfo.VERSION
            name = AppInfo.NAME
            description = AppInfo.DESCRIPTION
        }
    }
}