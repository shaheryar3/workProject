import os
from Flask import flask, render_template, request, redirect, url_for
from whoosh.index import create_in, open_dir, exists_in
from whoosh.fields import Schema, TEXT, ID
from whoosh.qparser import QueryParser
from whoosh.analysis import StemmingAnalyzer

app = Flask(__name__)

INDEX_DIR = "flask_whoosh_index_test_criteria"

schema = Schema(
    test_id=ID(stored=True, unique=True),
    acceptance_criteria=TEXT(stored=True, analyzer=StemmingAnalyzer())
)

sample_data = [
    {"test_id": "TC001", "acceptance_criteria": "User should be able to login with valid credentials."},
    {"test_id": "TC002", "acceptance_criteria": "System must reject login with invalid username or password."},
    {"test_id": "TC003", "acceptance_criteria": "User profile page should display username and email clearly."},
    {"test_id": "TC004", "acceptance_criteria": "Password reset functionality must send an email to the registered user address."},
    {"test_id": "TC005", "acceptance_criteria": "The main search functionality should return relevant results for entered product names."},
    {"test_id": "TC006", "acceptance_criteria": "Admin can deactivate user accounts from the dashboard."},
    {"test_id": "TC007", "acceptance_criteria": "System should log all failed login attempts for security auditing."}
]

def init_index():
    if not os.path.exists(INDEX_DIR):
        os.mkdir(INDEX_DIR)
    if not exists_in(INDEX_DIR):
        ix = create_in(INDEX_DIR, schema)
        writer = ix.writer()
        for case in sample_data:
            writer.add_document(test_id=case["test_id"], acceptance_criteria=case["acceptance_criteria"])
        writer.commit()

init_index()
ix = open_dir(INDEX_DIR)

@app.route("/", methods=["GET"])
def index():
    return render_template("index.html")

@app.route("/search", methods=["GET", "POST"])
def search():
    if request.method == "POST":
        query = request.form.get("query", "")
        return redirect(url_for("search", query=query))
    query = request.args.get("query", "")
    results = []
    if query:
        with ix.searcher() as searcher:
            qp = QueryParser("acceptance_criteria", schema=ix.schema)
            q = qp.parse(query)
            hits = searcher.search(q, limit=None)
            for hit in hits:
                results.append({
                    "test_id": hit["test_id"],
                    "acceptance_criteria": hit["acceptance_criteria"]
                })
    return render_template("results.html", results=results, query=query)

if __name__ == "__main__":
    app.run(debug=True)