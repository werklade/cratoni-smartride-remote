<#
.SYNOPSIS
Copies the shared Werklade design tokens or checks the local copy.
.EXAMPLE
pwsh -File scripts/sync-design.ps1
.EXAMPLE
pwsh -File scripts/sync-design.ps1 -Check
#>
#Requires -Version 7.0
[CmdletBinding(SupportsShouldProcess)]
param(
    [switch]$Check
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

try {
    $projectRoot = Join-Path -Path $PSScriptRoot -ChildPath '..'
    $designRoot = Join-Path -Path $projectRoot -ChildPath '..' -AdditionalChildPath 'design'
    if (-not (Test-Path -LiteralPath $designRoot -PathType Container)) {
        if ($Check) {
            throw "design repo not found at $designRoot; not checked"
        }
        Write-Output 'Note: sibling design repository is missing; design sync skipped.'
        exit 0
    }

    $source = Join-Path -Path $designRoot -ChildPath 'generated' -AdditionalChildPath 'werklade.tokens.json'
    $destination = Join-Path -Path $projectRoot -ChildPath 'app' -AdditionalChildPath 'src', 'test', 'resources', 'design', 'werklade.tokens.json'
    if (-not (Test-Path -LiteralPath $source -PathType Leaf)) {
        throw "Design token source is missing: $source"
    }

    if ($Check) {
        if (-not (Test-Path -LiteralPath $destination -PathType Leaf)) {
            throw 'Design token copy is missing. Run scripts/sync-design.ps1 to synchronize it.'
        }
        $sourceText = (Get-Content -LiteralPath $source -Raw).Replace("`r`n", "`n")
        $destinationText = (Get-Content -LiteralPath $destination -Raw).Replace("`r`n", "`n")
        if ($sourceText -cne $destinationText) {
            throw 'Design token copy differs from the design repository. Run scripts/sync-design.ps1 to synchronize it.'
        }
        Write-Output 'Design tokens are in sync (line endings normalized).'
        exit 0
    }

    if ($PSCmdlet.ShouldProcess($destination, 'Copy shared design tokens (overwrite local copy)')) {
        $destinationDirectory = Split-Path -Path $destination -Parent
        [System.IO.Directory]::CreateDirectory($destinationDirectory) | Out-Null
        Copy-Item -LiteralPath $source -Destination $destination -Force
        Write-Output 'Design tokens synchronized.'
    }
    exit 0
}
catch {
    [Console]::Error.WriteLine($_.Exception.Message)
    exit 1
}
