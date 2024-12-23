# Generated by Django 5.1.1 on 2024-11-19 17:19

import django.db.models.deletion
from django.conf import settings
from django.db import migrations, models


class Migration(migrations.Migration):
    dependencies = [
        ("ask_me", "0004_remove_classificationhistory_dataattr_and_more"),
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.AddField(
            model_name="classificationhistory",
            name="actual_type",
            field=models.CharField(default="a", max_length=128),
            preserve_default=False,
        ),
        migrations.AlterField(
            model_name="classificationhistory",
            name="user",
            field=models.ForeignKey(
                on_delete=django.db.models.deletion.DO_NOTHING,
                to=settings.AUTH_USER_MODEL,
            ),
        ),
    ]
