module.exports = {

	config : {
		SchemaVersion : "1.0.0",
		Name : "Plain"
	},

	software : {

		source : {
			Source : "mvn",
			Artifact : "target/plain.war",
			config : {
				Name : "plain.properties",
				Content : [
						{ Line : "{" },
						{ Line : "\"db.url\": \"jdbc:mysql://$$VALUE$$:3306/plain?useSSL=false\"", Source : "mysql" },
						{ Line : "}" }
				],
				AttachAsEnvVar : [ "JAVA_OPTS", "-Dplain=$$SELF_NAME$$" ]
			}
		},

		mysql : {
			Source : "mysql",
			Mysql : {
				Schema : "plain",
				Create : [ "src/db/init-ddl.sql", "src/db/init-data.sql" ],
			}
		},

		tomcat : {
			Source : "tomcat",
			Deploy : "source"
		}
	}

}