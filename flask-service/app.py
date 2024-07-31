from flask import Flask, request, jsonify
from pytubefix import YouTube
import urllib.parse

app = Flask(__name__)


def is_url(url_string):
    try:
        # Attempt to parse the URL
        result = urllib.parse.urlparse(url_string)
        # Check if all parts are defined (excluding fragment)
        return all([result.scheme, result.netloc, result.path])
    except (ValueError, AttributeError):
        # If parsing fails, the string is not a URL
        return False


@app.route("/api/extract", methods=["GET"])
def extract_video_info():
    """This method accept also the video ID or URl of the video."""

    YT_PREFIX = "https://www.youtube.com/watch?v="

    vid = request.args.get("url")
    if not vid:
        response = {
            "status": 400,
            "error": "Bad Request",
            "description": "Missing 'url' parameter.",
        }
        return jsonify(response), 400

    if not is_url(vid):
        vid = YT_PREFIX + vid

    yt = YouTube(url=vid)
    audio = yt.streams.get_audio_only()

    res = {
        "title": yt.title,
        "artist": yt.author,
        "duration": yt.length,
        "playback_url": audio.url if audio is not None else None,
    }
    return jsonify(res), 200


if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0")
