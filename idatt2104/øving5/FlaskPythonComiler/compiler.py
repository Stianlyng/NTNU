from flask import Flask, render_template, redirect, url_for
from flask_bootstrap import Bootstrap
from flask_wtf import FlaskForm
from wtforms import StringField, SubmitField, TextAreaField
from wtforms.validators import DataRequired
import os

app = Flask(__name__)

app.config['SECRET_KEY'] = 'C2HWGVoMGfNTBsrYQg8EcMrdTimkZfAb'

Bootstrap(app)
class CompilerForm(FlaskForm):
    code = TextAreaField('Enter code:', validators=[DataRequired()])
    submit = SubmitField('Submit')


# all Flask routes below

@app.route('/', methods=['GET', 'POST'])
def index():

    form = CompilerForm()
    message = ""
    if form.validate_on_submit():
        code = form.code.data
        message = os.popen(f"docker exec -it pycodebox python -c '{code}'").read()
    return render_template('index.html', form=form, message=message)

@app.errorhandler(404)
def page_not_found(e):
    return render_template('404.html'), 404

@app.errorhandler(500)
def internal_server_error(e):
    return render_template('500.html'), 500


# keep this as is
if __name__ == '__main__':
    app.run(debug=True)
