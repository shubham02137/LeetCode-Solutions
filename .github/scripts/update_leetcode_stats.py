import json
import re
import urllib.request
from collections import defaultdict
from pathlib import Path

USERNAME = "shubhamdevit"

README_FILE = Path("README.md")
STATS_FILE = Path("stats.json")

LEETCODE_API = "https://leetcode.com/graphql"

QUERY = """
query userProblemsSolved($username: String!) {
  matchedUser(username: $username) {
    submitStatsGlobal {
      acSubmissionNum {
        difficulty
        count
      }
    }
    tagProblemCounts {
      advanced {
        tagName
        tagSlug
        problemsSolved
      }
      intermediate {
        tagName
        tagSlug
        problemsSolved
      }
      fundamental {
        tagName
        tagSlug
        problemsSolved
      }
    }
  }
}
"""


def fetch_leetcode_data():
    payload = json.dumps({
        "query": QUERY,
        "variables": {
            "username": USERNAME
        }
    }).encode("utf-8")

    request = urllib.request.Request(
        LEETCODE_API,
        data=payload,
        headers={
            "Content-Type": "application/json",
            "User-Agent": "Mozilla/5.0",
            "Referer": f"https://leetcode.com/u/{USERNAME}/"
        }
    )

    with urllib.request.urlopen(request, timeout=30) as response:
        return json.loads(response.read().decode("utf-8"))


def difficulty_stats(user):
    result = {
        "All": 0,
        "Easy": 0,
        "Medium": 0,
        "Hard": 0
    }

    stats = user["submitStatsGlobal"]["acSubmissionNum"]

    for item in stats:
        difficulty = item["difficulty"]
        if difficulty in result:
            result[difficulty] = item["count"]

    return result


def topic_stats(user):
    topics = {}

    tag_counts = user.get("tagProblemCounts", {})

    for category in [
        "fundamental",
        "intermediate",
        "advanced"
    ]:
        for tag in tag_counts.get(category, []):
            topics[tag["tagName"]] = tag["problemsSolved"]

    return topics


def create_dashboard(stats):
    return f"""<!-- DASHBOARD_START -->
| Metric | Solved |
|:--|--:|
| 🧩 **Total Problems** | **{stats['All']}** |
| 🟢 Easy | **{stats['Easy']}** |
| 🟡 Medium | **{stats['Medium']}** |
| 🔴 Hard | **{stats['Hard']}** |
<!-- DASHBOARD_END -->"""


DISPLAY_TOPICS = [
    ("📦", "Array"),
    ("🔤", "String"),
    ("#️⃣", "Hash Table"),
    ("➗", "Math"),
    ("🔃", "Sorting"),
    ("👉", "Two Pointers"),
    ("🪟", "Sliding Window"),
    ("➕", "Prefix Sum"),
    ("🔎", "Binary Search"),
    ("🔗", "Linked List"),
    ("📚", "Stack"),
    ("🚶", "Queue"),
    ("⛰️", "Heap (Priority Queue)"),
    ("🌳", "Tree"),
    ("🌲", "Binary Tree"),
    ("🌿", "Binary Search Tree"),
    ("🕸️", "Graph"),
    ("🔵", "Breadth-First Search"),
    ("🟣", "Depth-First Search"),
    ("🔗", "Union Find"),
    ("🧩", "Dynamic Programming"),
    ("🎯", "Greedy"),
    ("↩️", "Backtracking"),
    ("🔡", "Trie"),
    ("💡", "Bit Manipulation"),
]


def create_topic_table(topics):
    lines = [
        "<!-- TOPIC_STATS_START -->",
        "| Topic | Solved |",
        "|:--|--:|"
    ]

    for icon, topic in DISPLAY_TOPICS:
        count = topics.get(topic, 0)
        lines.append(
            f"| {icon} {topic} | **{count}** |"
        )

    lines.append("<!-- TOPIC_STATS_END -->")

    return "\n".join(lines)


def replace_section(content, start_marker, end_marker, replacement):
    pattern = (
        re.escape(start_marker)
        + r".*?"
        + re.escape(end_marker)
    )

    if not re.search(pattern, content, flags=re.DOTALL):
        print(
            f"Warning: {start_marker} / "
            f"{end_marker} not found."
        )
        return content

    return re.sub(
        pattern,
        replacement,
        content,
        flags=re.DOTALL
    )


def main():
    print(f"Fetching LeetCode statistics for {USERNAME}...")

    response = fetch_leetcode_data()

    if response.get("errors"):
        raise RuntimeError(response["errors"])

    user = response.get("data", {}).get("matchedUser")

    if not user:
        raise RuntimeError(
            f"LeetCode user '{USERNAME}' was not found."
        )

    difficulties = difficulty_stats(user)
    topics = topic_stats(user)

    print("Total:", difficulties["All"])
    print("Easy:", difficulties["Easy"])
    print("Medium:", difficulties["Medium"])
    print("Hard:", difficulties["Hard"])

    stats_json = {
        "username": USERNAME,
        "totalSolved": difficulties["All"],
        "easySolved": difficulties["Easy"],
        "mediumSolved": difficulties["Medium"],
        "hardSolved": difficulties["Hard"],
        "topics": topics
    }

    STATS_FILE.write_text(
        json.dumps(stats_json, indent=2, ensure_ascii=False)
        + "\n",
        encoding="utf-8"
    )

    readme = README_FILE.read_text(encoding="utf-8")

    dashboard = create_dashboard(difficulties)
    topic_table = create_topic_table(topics)

    readme = replace_section(
        readme,
        "<!-- DASHBOARD_START -->",
        "<!-- DASHBOARD_END -->",
        dashboard
    )

    readme = replace_section(
        readme,
        "<!-- TOPIC_STATS_START -->",
        "<!-- TOPIC_STATS_END -->",
        topic_table
    )

    README_FILE.write_text(
        readme,
        encoding="utf-8"
    )

    print("README.md updated successfully.")
    print("stats.json updated successfully.")


if __name__ == "__main__":
    main()
