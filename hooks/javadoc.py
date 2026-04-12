import shutil
import subprocess
from pathlib import Path

def on_post_build(config):
    subprocess.run(["./mvnw", "compile", "javadoc:javadoc"], check=True)
    shutil.move(Path("target/reports/apidocs"), Path(config["site_dir"]) / "api")
